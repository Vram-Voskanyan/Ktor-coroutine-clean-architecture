package com.vram.cleanapp.data.service.network

import com.vram.cleanapp.domain.common.data.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.nio.channels.UnresolvedAddressException

// can be `object` instead of class
@InternalSerializationApi
class KtorClient(private val baseUrl: String) {

    // suspend inline fun <reified> ¯\_(ツ)_/¯ Keyword hell.
    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE") // TODO: deep dive on this
    suspend inline fun <reified R : Any> get(urlPath: String): R = try {
        val response = client.get<HttpResponse> { url { path(urlPath) } }
        if (!response.status.isSuccess()) {
            handleError(response)
        }
        Json.decodeFromString(R::class.serializer(), response.readText())
    } catch (ex: UnresolvedAddressException) {
        throw NoInternet()
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

    private fun handleError(response: HttpResponse) {
        when (response.status) {
            // TODO test no_internet, 404, 500 error codes.
            HttpStatusCode.BadRequest -> throw BadRequest()
            HttpStatusCode.Unauthorized -> throw Unauthorized()
            HttpStatusCode.InternalServerError -> throw InternalServerError()
            else -> throw Unknown()
        }
    }

    private suspend fun postRequest(urlPath: String, bodyData: Any): HttpResponse =
        client.post {
            url {
                path(urlPath)
            }
            body = bodyData
        }

    private suspend fun getRequest(urlPath: String): HttpResponse =
        client.get { url { path(urlPath) } }

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
