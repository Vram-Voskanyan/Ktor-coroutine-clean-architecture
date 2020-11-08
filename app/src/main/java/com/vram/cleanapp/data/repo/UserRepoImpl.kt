package com.vram.cleanapp.data.repo

import com.vram.cleanapp.data.network.NetworkApi
import com.vram.cleanapp.domain.common.data.BaseRepo
import com.vram.cleanapp.domain.repo.UserRepo
import com.vram.cleanapp.service.cache.SharedPrefs

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

    override suspend fun userNotesFromCache() {
        sharedPrefs.retrieveJsonAsString("user_details") // TODO.
    }

    override suspend fun userDetailsFromNetwork() {
        TODO("Not yet implemented")
    }

    override suspend fun userNotesFromNetwork() {
        TODO("Not yet implemented")
    }

}