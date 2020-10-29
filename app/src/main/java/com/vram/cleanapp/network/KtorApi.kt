package com.vram.cleanapp.network

interface NetworkApi {
    fun setToken(token: String)
    fun removeToken()
    fun login(userName: String, password: String)
}

// :(
class NetworkApiImpl(private val ktorClient: KtorClient) : NetworkApi {

}