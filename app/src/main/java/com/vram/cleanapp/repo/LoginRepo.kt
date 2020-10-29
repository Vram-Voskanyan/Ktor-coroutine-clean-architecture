package com.vram.cleanapp.repo

import com.vram.cleanapp.base.BaseRepo

interface LoginRepo {
    fun login(userName: String, password: String)
}

class LoginRepoImpl(val networkApi: NetworkApi) : LoginRepo, BaseRepo() {
    override fun login(userName: String, password: String) = networkApi.login(userName, password)
}