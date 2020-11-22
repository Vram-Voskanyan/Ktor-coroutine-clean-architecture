package com.vram.cleanapp.data.network

import com.vram.cleanapp.*
import com.vram.cleanapp.data.model.EmailCheckerModel
import com.vram.cleanapp.data.model.UserInfoModel
import com.vram.cleanapp.data.model.UserNotesModel
import com.vram.cleanapp.data.model.UserTokenModel
import com.vram.cleanapp.service.network.KtorClient
import kotlinx.coroutines.delay
import kotlinx.serialization.InternalSerializationApi
import kotlin.random.Random

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

    override suspend fun userInfo(): UserInfoModel {
        // THIS IS CALL IMITATION
        // Imitation of long call. w8 from 2 to 7 sec.
        delay(Random.nextLong(2000, 7000))
        return ktorClient.get(GET_USER_INFO_URL)
    }

    override suspend fun userNotes(): UserNotesModel {
        // THIS IS CALL IMITATION
        // Imitation of long call.
        delay(Random.nextLong(1000, 5000))
        return ktorClient.get(GET_USER_NOTES_URL)
    }

    // SOON :] simple for post request.
    // fun addNote() { return ktorClient.post() }

}