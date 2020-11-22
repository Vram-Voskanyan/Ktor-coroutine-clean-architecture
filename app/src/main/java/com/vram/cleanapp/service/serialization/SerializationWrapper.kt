package com.vram.cleanapp.service.serialization

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

// TODO: :[ ... Not proper wrapped: KSerializer<T>
interface SerializationWrapper {
    fun <T : Any> stringToObject(serializer: KSerializer<T>, json: String): T
    fun <T : Any> objectToString(serializer: KSerializer<T>, json: T): String
}

class KXSerializationImpl : SerializationWrapper {
    override fun <T : Any> stringToObject(serializer: KSerializer<T>, json: String) =
        Json { ignoreUnknownKeys = true; isLenient = true }.decodeFromString(serializer, json)

    override fun <T : Any> objectToString(serializer: KSerializer<T>, json: T): String =
        Json.encodeToString(serializer, json)
}

@InternalSerializationApi
inline fun <reified R : Any> SerializationWrapper.stringToObject(json: String) =
    Json { ignoreUnknownKeys = true; isLenient = true }.decodeFromString(R::class.serializer(), json)

@InternalSerializationApi
inline fun <reified R : Any> SerializationWrapper.objectToString(json: R) =
    Json.encodeToString(R::class.serializer(), json)



