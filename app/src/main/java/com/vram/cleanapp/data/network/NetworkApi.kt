package com.vram.cleanapp.data.network

import com.vram.cleanapp.data.model.EmailCheckerModel
import com.vram.cleanapp.data.model.UserInfoModel
import com.vram.cleanapp.data.model.UserNotesModel
import com.vram.cleanapp.data.model.UserTokenModel

// All network related interface.
/**
 * encapsulation Network need for API-i reuse, like cross platform migration...
 *
 * or just inject `ktorClient` into repo and call ktorClient.get<MODEL>(URL)
 **/
interface NetworkApi {
    suspend fun setToken(token: String)
    suspend fun removeToken()
    suspend fun isEmailExist(email: String): EmailCheckerModel
    suspend fun login(email: String, password: String): UserTokenModel
    suspend fun userInfo(): UserInfoModel
    suspend fun userNotes(): UserNotesModel
}
