package com.vram.cleanapp.service.cache

import android.content.Context
import androidx.core.content.edit
import com.vram.cleanapp.domain.common.data.NoSuchElement
import com.vram.cleanapp.service.serialization.SerializationWrapper
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

interface SharedPrefs {
    suspend fun saveJson(key: String, json: String)
    suspend fun retrieveJsonAsString(key: String): String?
    suspend fun <T : Any> retrieveJsonAsObject(serializer: KSerializer<T>, key: String): T
}

@InternalSerializationApi
suspend inline fun <reified R : Any> SharedPrefs.retrieveJsonAsObject(json: String): R =
    retrieveJsonAsObject(R::class.serializer(), json)

class SharedPrefsImpl(
    context: Context,
    private val serializationWrapper: SerializationWrapper,
    private val fileName: String = "cache"
) : SharedPrefs {
    // for prevent setting of activity context.
    private val applicationContext: Context = context.applicationContext
    private fun pref() = applicationContext.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    override suspend fun saveJson(key: String, json: String) {
        pref().edit {
            putString(key, json)
        }
    }

    override suspend fun retrieveJsonAsString(key: String): String? =
        pref().getString(key, null)

    override suspend fun <T : Any> retrieveJsonAsObject(
        serializer: KSerializer<T>,
        key: String
    ): T = pref().getString(key, null)?.let {
        serializationWrapper.stringToObject(serializer, it)
    } ?: throw NoSuchElement()

}