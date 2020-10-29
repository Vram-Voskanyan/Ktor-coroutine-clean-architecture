package com.vram.cleanapp.repo

import com.vram.cleanapp.network.NetworkApi
import com.vram.cleanapp.shared.BaseRepo

interface LoginRepo {
    fun login(userName: String, password: String)
}

class LoginRepoImpl(private val networkApi: NetworkApi) : LoginRepo, BaseRepo() {
    override fun login(userName: String, password: String) = networkApi.login(userName, password)
}