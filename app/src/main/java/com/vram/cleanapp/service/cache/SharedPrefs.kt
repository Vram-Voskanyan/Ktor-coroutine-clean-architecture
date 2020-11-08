package com.vram.cleanapp.service.cache

import android.content.Context
import androidx.core.content.edit

interface SharedPrefs {
    suspend fun saveJson(key: String, json: String)
    suspend fun retrieveJsonAsString(key: String): String?
}

class SharedPrefsImpl(context: Context, private val fileName: String = "cache") : SharedPrefs {
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

    // TODO also need to handle this sort of api.
//    override suspend fun retrieveJsonAsString(key: String): String =
//        sharedPreferences.getString(key, null) ?: throw NoSuchElementException()

}