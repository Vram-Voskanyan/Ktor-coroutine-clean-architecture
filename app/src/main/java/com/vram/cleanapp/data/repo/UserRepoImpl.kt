package com.vram.cleanapp.data.repo

import com.vram.cleanapp.data.model.UserNotesModel
import com.vram.cleanapp.data.model.toUserInfoEntity
import com.vram.cleanapp.data.model.toUserNotesEntity
import com.vram.cleanapp.data.network.NetworkApi
import com.vram.cleanapp.domain.common.data.BaseRepo
import com.vram.cleanapp.domain.entity.UserNotes
import com.vram.cleanapp.domain.repo.UserRepo
import com.vram.cleanapp.service.cache.SharedPrefs
import com.vram.cleanapp.service.cache.retrieveJsonAsObject
import com.vram.cleanapp.service.cache.saveObjectAsJson
import kotlinx.serialization.InternalSerializationApi

@InternalSerializationApi
class UserRepoImpl(
    private val networkApi: NetworkApi,
    private val sharedPrefs: SharedPrefs
) : UserRepo, BaseRepo() {

    override suspend fun saveToken(token: String) {
        networkApi.setToken(token)
    }

    override suspend fun removeToken() {
        networkApi.removeToken()
    }

    override suspend fun userNotesFromCache(): UserNotes {
        val userNotes =
            sharedPrefs.retrieveJsonAsObject<UserNotesModel>("user_notes") // TODO move user_notes key...
        return userNotes.toUserNotesEntity()
    }

    override suspend fun saveNotes(userNotes: UserNotes) {
        sharedPrefs.saveObjectAsJson("user_notes", UserNotesModel(userNotes)) // UserNotesModel
    }

    override suspend fun removeNotes() {
        sharedPrefs.remove("user_notes")
    }

    override suspend fun userDetailsFromNetwork() = networkApi.userInfo().toUserInfoEntity()

    override suspend fun userNotesFromNetwork() = networkApi.userNotes().toUserNotesEntity()

}