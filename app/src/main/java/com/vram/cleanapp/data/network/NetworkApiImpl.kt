package com.vram.cleanapp.data.network

import com.vram.cleanapp.EMAIL_EXIST_URL
import com.vram.cleanapp.EMAIL_NOT_EXIST_URL
import com.vram.cleanapp.GET_USER_TOKEN_URL
import com.vram.cleanapp.INVALID_CREDENTIALS
import com.vram.cleanapp.data.model.EmailCheckerModel
import com.vram.cleanapp.data.model.UserTokenModel
import com.vram.cleanapp.domain.common.data.todoCrash
import com.vram.cleanapp.service.network.KtorClient
import kotlinx.serialization.InternalSerializationApi

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
            ktorClient.get<EmailCheckerModel>(EMAIL_NOT_EXIST_URL)
        } else {
            ktorClient.get(EMAIL_EXIST_URL)
        }
        return result
    }

    override suspend fun login(email: String, password: String): UserTokenModel {
        // THIS IS CALL IMITATION
        val result = if (password == "1111") {
            ktorClient.get(GET_USER_TOKEN_URL)
        } else {
            ktorClient.get<UserTokenModel>(INVALID_CREDENTIALS)
        }
        return result
    }

    override suspend fun userInfo() {
        todoCrash()
    }

    override suspend fun userNotes() = todoCrash()

}