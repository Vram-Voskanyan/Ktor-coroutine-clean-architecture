package com.vram.cleanapp.data.service

import com.vram.cleanapp.shared.network.KtorClient

interface NetworkApi {
    fun setToken(token: String)
    fun removeToken()
    fun login(userName: String, password: String)
    fun userInfo()
    fun userList()
}

// :(
class NetworkApiImpl(private val ktorClient: KtorClient) : NetworkApi {

    override fun setToken(token: String) {
        TODO("Not yet implemented")
    }

    override fun removeToken() {
        TODO("Not yet implemented")
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