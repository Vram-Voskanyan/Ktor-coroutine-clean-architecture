package com.vram.cleanapp.data.service.network

import com.vram.cleanapp.data.service.network.model.EmailCheckerModel
import com.vram.cleanapp.data.service.network.model.UserTokenModel
import kotlinx.serialization.InternalSerializationApi

// TODO: need to move to Domain
interface NetworkApi {
    suspend fun setToken(token: String)
    suspend fun removeToken()
    suspend fun isEmailExist(email: String): EmailCheckerModel
    suspend fun login(email: String, password: String): UserTokenModel
    suspend fun userInfo()
    suspend fun userList()
}

@InternalSerializationApi
class NetworkApiImpl(private val ktorClient: KtorClient) : NetworkApi {

    override suspend fun setToken(token: String) {
        ktorClient.addDefaultHeader("Authorization", "Basic $token")
    }

    override suspend fun removeToken() {
        ktorClient.removeDefaultHeader("Authorization")
    }

    override suspend fun isEmailExist(email: String): EmailCheckerModel {
        // THIS IS CALL IMITATION
        val result = if (email != "vram.arm@gmail.com") {
            ktorClient.get<EmailCheckerModel>("f8cebfe2-036f-4845-8b77-7271bd87a6d5")
        } else {
            ktorClient.get("01f3c142-ff9d-4925-8298-1132e6d740df")
        }
        return result
    }

    override suspend fun login(email: String, password: String): UserTokenModel {
        // THIS IS CALL IMITATION
        val result = if (password == "1111") {
            ktorClient.get("67711067-83dd-481c-9ccd-403c416dc36c")
        } else {
            // todo: handle from error
            ktorClient.get<UserTokenModel>("8caca343-5edf-4764-a424-5646bd017057")
        }
        return result
    }

    override suspend fun userInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun userList() {
        TODO("Not yet implemented")
    }

}