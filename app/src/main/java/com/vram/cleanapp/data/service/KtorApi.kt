package com.vram.cleanapp.data.service

import com.vram.cleanapp.domain.common.data.BaseException
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

// TODO: need to move to Domain
interface NetworkApi {
    suspend fun setToken(token: String)
    suspend fun removeToken()
    suspend fun isEmailExist(email: String): EmailChecker
    suspend fun login(email: String, password: String)
    suspend fun userInfo()
    suspend fun userList()
}

@InternalSerializationApi
class NetworkApiImpl(private val ktorClient: KtorClient) : NetworkApi {

    override suspend fun setToken(token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeToken() {
        TODO("Not yet implemented")
    }

    override suspend fun isEmailExist(email: String): EmailChecker {
        // THIS IS CALL IMITATION
        val result = if(email != "vram.arm@gmail.com") {
            ktorClient.get<EmailChecker>("f8cebfe2-036f-4845-8b77-7271bd87a6d5")
        } else {
            TODO()
        }
        return result
    }

    override suspend fun login(userName: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun userInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun userList() {
        TODO("Not yet implemented")
    }

}
// TODO  move to sep. file
// TODO change name.
@Serializable
data class EmailChecker(val isEmailExist: Boolean)