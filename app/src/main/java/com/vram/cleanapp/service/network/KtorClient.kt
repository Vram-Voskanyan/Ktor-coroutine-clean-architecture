package com.vram.cleanapp.service.network

import com.vram.cleanapp.domain.common.data.*
import com.vram.cleanapp.service.serialization.SerializationWrapper
import com.vram.cleanapp.service.serialization.stringToObject
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.InternalSerializationApi
import java.nio.channels.UnresolvedAddressException

// can be `object` instead of class
@InternalSerializationApi
@Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE") // TODO: deep dive on this
class KtorClient(private val baseUrl: String, private val serializationWrapper: SerializationWrapper) {

    // suspend inline fun <reified> ¯\_(ツ)_/¯ Keyword hell.
    suspend inline fun <reified R : Any> get(urlPath: String): R = call {
        client.get { url { path(urlPath) } }
    }

    suspend inline fun <reified R : Any> post(urlPath: String, bodyData: Any): R = call {
        client.post { url { path(urlPath) }; body = bodyData }
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

    @KtorExperimentalAPI // CIO
    private fun initHttpClient() =
        // CIO is experimental can be change to: OkHttp, Android Engine
        HttpClient(CIO) {
            configureJson()
            configureLogging()
            configureDefaultRequest()
        }

    private suspend inline fun <reified R : Any> call(request: () -> HttpResponse): R = try {
        val response = request()
        if (!response.status.isSuccess()) {
            handleError(response)
        }
        serializationWrapper.stringToObject(response.readText())
    } catch (ex: UnresolvedAddressException) {
        throw NoInternet()
    }

    private fun handleError(response: HttpResponse) {
        when (response.status) {
            HttpStatusCode.BadRequest -> throw BadRequest()
            HttpStatusCode.Unauthorized -> throw Unauthorized()
            HttpStatusCode.InternalServerError -> throw InternalServerError()
            else -> throw UnknownNetworkException()
        }
    }

    private suspend fun postRequest(urlPath: String, bodyData: Any): HttpResponse =
        client.post {
            url {
                path(urlPath)
            }
            body = bodyData
        }

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
        // moved content type on init function.
        //addDefaultHeader("Content-Type", "application/json")
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
