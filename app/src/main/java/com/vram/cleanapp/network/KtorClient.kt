package com.vram.cleanapp.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vart.madapplication.core.data.Action
import com.vart.madapplication.delivery.activeorders.data.ResponseResult
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.lang.reflect.Type

class BadRequest : Exception()
class InternalServerError : Exception()
class NotFound : Exception()
class Unknown : Exception()

// can be `object` instead of class
class KtorClient(private val baseUrl: String) {

    suspend fun <R> post(url: String, body: Any): Action<R> = safeApiCall(object : TypeToken<ResponseResult<R>>() {}.type) {
        postRequest(url, body)
    }

    suspend fun <R> get(returnBodyType: Type, urlPath: String): Action<R> = safeApiCall(getTypeToken(returnBodyType)) {
        getRequest(urlPath)
    }

    fun addHeader(key: String, value: String) {
        defaultHeaders[key] = value
    }

    fun removeHeader(key: String) {
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

    private suspend fun <R> safeApiCall(type: Type, call: suspend () -> HttpResponse): Action<R> =
        try {
            val response = call()
            if (!response.status.isSuccess()) {
                handleError(response)
            } else {
                decodeResponseBody(type, response)
            }
        } catch (ex: Exception) {
            Action.Error(-2, ex)
        }

    // private suspend inline fun <reified> ¯\_(ツ)_/¯
    private suspend fun <R> decodeResponseBody(type: Type, httpResponse: HttpResponse): Action<R> {
        // TODO: need to change to byteArray
//        val typeR: Type = object : TypeToken<ResponseResult<R>>() {}.type
        val data = httpResponse.readText()
        return Action.Success(Gson().fromJson<ResponseResult<R>>(data, type).result)
    }

    private fun handleError(response: HttpResponse): Action.Error =
        when (response.status) {
            HttpStatusCode.BadRequest -> Action.Error(403, BadRequest())
            HttpStatusCode.NotFound -> Action.Error(404, NotFound())
            HttpStatusCode.InternalServerError -> Action.Error(501, InternalServerError())
            else -> Action.Error(-1, Unknown())
        }

    private fun getTypeToken(type: Type) = TypeToken.getParameterized(ResponseResult::class.java, type).type

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
            // need to have parser with reflection, otherwise it will be painful
            // to support generic responses: BaseResponse<T>
            // serializer = KotlinxSerializer()
            serializer = GsonSerializer()
        }
    }

    private fun HttpClientConfig<*>.configureLogging() {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    private fun HttpClientConfig<*>.configureDefaultRequest() {
        addHeader("Content-Type", "application/json")
        defaultRequest {
            url.takeFrom(URLBuilder().takeFrom(baseUrl).apply {
                encodedPath += url.encodedPath
            })
            headers.clear()
            defaultHeaders.forEach {
                headers.append(it.key, it.value)
            }
        }
    }

}
