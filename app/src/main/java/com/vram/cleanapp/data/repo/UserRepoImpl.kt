package com.vram.cleanapp.data.repo

import com.vram.cleanapp.data.model.toUserInfoEntity
import com.vram.cleanapp.data.network.NetworkApi
import com.vram.cleanapp.domain.common.data.BaseRepo
import com.vram.cleanapp.domain.repo.UserRepo

class UserRepoImpl(
    private val networkApi: NetworkApi
) : UserRepo, BaseRepo() {

    override suspend fun saveToken(token: String) {
        networkApi.setToken(token)
    }

    override suspend fun removeToken() {
        networkApi.removeToken()
    }

    override suspend fun userDetailsFromNetwork() = networkApi.userInfo().toUserInfoEntity()

}