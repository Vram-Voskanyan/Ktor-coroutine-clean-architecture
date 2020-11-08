package com.vram.cleanapp.service.serialization

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

// TODO: :[ Not proper wrapped: KSerializer<T>
interface SerializationWrapper {
    fun <T : Any> stringToObject(serializer: KSerializer<T>, json: String) : T
}

class KXSerializationImpl: SerializationWrapper {
    override fun <T : Any> stringToObject(serializer: KSerializer<T>, json: String) =
        Json.decodeFromString(serializer, json)
}

@InternalSerializationApi
inline fun <reified R : Any> SerializationWrapper.stringToObject(json: String) =
    Json.decodeFromString(R::class.serializer(), json)



