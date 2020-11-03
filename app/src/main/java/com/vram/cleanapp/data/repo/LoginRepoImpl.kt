package com.vram.cleanapp.data.repo

import com.vram.cleanapp.data.service.network.NetworkApi
import com.vram.cleanapp.data.service.network.model.toBoolean
import com.vram.cleanapp.data.service.network.model.toUserTokenEntity
import com.vram.cleanapp.domain.repo.LoginRepo
import com.vram.cleanapp.domain.common.data.BaseRepo

class LoginRepoImpl(private val networkApi: NetworkApi) : LoginRepo, BaseRepo() {
    override suspend fun login(email: String, password: String) = networkApi.login(email, password).toUserTokenEntity()
    override suspend fun isEmailExist(email: String): Boolean = networkApi.isEmailExist(email).toBoolean()
}