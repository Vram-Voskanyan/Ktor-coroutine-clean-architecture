package com.vram.cleanapp.network

import com.vram.cleanapp.shared.data.Action
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class BadRequest : Exception()
class InternalServerError : Exception()
class NotFound : Exception()
class Unknown : Exception()

// can be `object` instead of class
class KtorClient(private val baseUrl: String) {

    suspend fun <R> post(url: String, body: Any): Action<R> = safeApiCall {
        postRequest(url, body)
    }

    suspend fun <R> get(urlPath: String): Action<R> = safeApiCall {
        getRequest(urlPath)
    }

    // we add new header for `ALL` calls
    fun addDefaultHeader(key: String, value: String) {
        defaultHeaders[key] = value
    }

    fun removeDefaultHeader(key: String) {
        defaultHeaders.remove(key)
    }

    private val defaultHeaders: MutableMap<String, String> = mutableMapOf()
    private val client: HttpClient by lazy { initHttpClient() }

    private fun initHttpClient() =
        HttpClient(CIO) {
            configureJson()
            configureLogging()
            configureDefaultRequest()
        }

    private suspend fun <R> safeApiCall(call: suspend () -> HttpResponse): Action<R> =
        try {
            val response = call()
            if (!response.status.isSuccess()) {
                handleError(response)
            } else {
                decodeResponseBody(response)
            }
        } catch (ex: Exception) {
            Action.Error(ex)
        }

    // private suspend inline fun <reified> ¯\_(ツ)_/¯
    private suspend inline fun <reified R> decodeResponseBody(httpResponse: HttpResponse): R {
        return Action.Success(TODO()) as R
    }

    private fun handleError(response: HttpResponse): Action.Error =
        when (response.status) {
            HttpStatusCode.BadRequest -> Action.Error(BadRequest())
            HttpStatusCode.NotFound -> Action.Error(NotFound())
            HttpStatusCode.InternalServerError -> Action.Error(InternalServerError())
            else -> Action.Error(Unknown())
        }

    private suspend fun postRequest(urlPath: String, bodyData: Any): HttpResponse =
        client.post {
            url {
                path(urlPath)
            }
            body = bodyData
        }

    private suspend fun getRequest(urlPath: String): HttpResponse = client.get { url { path(urlPath) } }

    private fun HttpClientConfig<*>.configureJson() {
        install(JsonFeature) {
             serializer = KotlinxSerializer()
        }
    }

    private fun HttpClientConfig<*>.configureLogging() {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    private fun HttpClientConfig<*>.configureDefaultRequest() {
        // todo: need to move this header set on app init.
        addDefaultHeader("Content-Type", "application/json")
        defaultRequest {
            // mostly base urls structure are: <HOST>/<API_VERSION>/?<ADDITIONAL_PATH>
            // so for base url we are using https://example.com/api/v3/[endpoint]
            url.takeFrom(URLBuilder().takeFrom(baseUrl).apply {
                encodedPath += url.encodedPath
            })
            headers.clear()
            // list of default headers, for example in our case it will be:
            // Content-Type, user authentication token.
            defaultHeaders.forEach {
                headers.append(it.key, it.value)
            }
        }
    }

}
