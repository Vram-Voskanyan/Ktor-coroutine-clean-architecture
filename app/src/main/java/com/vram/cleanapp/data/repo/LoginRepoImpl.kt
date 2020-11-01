package com.vram.cleanapp.data.repo

import com.vram.cleanapp.data.service.NetworkApi
import com.vram.cleanapp.domain.repo.LoginRepo
import com.vram.cleanapp.domain.common.data.BaseRepo

class LoginRepoImpl(private val networkApi: NetworkApi) : LoginRepo, BaseRepo() {
    override fun login(email: String, password: String) = networkApi.login(email, password)
    override fun isEmailExist(email: String): Boolean = networkApi.isEmailExist(email)
}