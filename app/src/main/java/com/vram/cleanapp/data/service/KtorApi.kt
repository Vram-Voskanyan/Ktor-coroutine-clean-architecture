package com.vram.cleanapp.data.service

import com.vram.cleanapp.domain.common.data.BaseException

// TODO: need to move to Domain
interface NetworkApi {
    fun setToken(token: String)
    fun removeToken()
    fun isEmailExist(email: String): Boolean
    fun login(email: String, password: String)
    fun userInfo()
    fun userList()
}

class NetworkApiImpl(private val ktorClient: KtorClient) : NetworkApi {

    override fun setToken(token: String) {
        TODO("Not yet implemented")
    }

    override fun removeToken() {
        TODO("Not yet implemented")
    }

    override fun isEmailExist(email: String): Boolean {
        // TODO: KTOR call.
        throw BaseException(5)
    }

    override fun login(userName: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun userInfo() {
        TODO("Not yet implemented")
    }

    override fun userList() {
        TODO("Not yet implemented")
    }

}