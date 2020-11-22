package com.vram.cleanapp.data.repo

import com.vram.cleanapp.domain.common.data.PREFS_KEY_USER_NOTES
import com.vram.cleanapp.data.model.UserNotesModel
import com.vram.cleanapp.data.model.toUserNotesEntity
import com.vram.cleanapp.data.network.NetworkApi
import com.vram.cleanapp.domain.common.data.BaseRepo
import com.vram.cleanapp.domain.entity.UserNotes
import com.vram.cleanapp.domain.repo.NotesRepo
import com.vram.cleanapp.service.cache.SharedPrefs
import com.vram.cleanapp.service.cache.retrieveJsonAsObject
import com.vram.cleanapp.service.cache.saveObjectAsJson
import kotlinx.serialization.InternalSerializationApi

@InternalSerializationApi
class NotesRepoImpl(
    private val networkApi: NetworkApi,
    private val sharedPrefs: SharedPrefs
) : NotesRepo, BaseRepo() {

    override suspend fun userNotesFromCache(): UserNotes {
        val userNotes =
            sharedPrefs.retrieveJsonAsObject<UserNotesModel>(PREFS_KEY_USER_NOTES)
        return userNotes.toUserNotesEntity()
    }

    override suspend fun saveNotes(userNotes: UserNotes) {
        sharedPrefs.saveObjectAsJson(PREFS_KEY_USER_NOTES, UserNotesModel(userNotes))
    }

    override suspend fun removeNotes() {
        sharedPrefs.remove(PREFS_KEY_USER_NOTES)
    }


    override suspend fun userNotesFromNetwork() = networkApi.userNotes().toUserNotesEntity()
}
