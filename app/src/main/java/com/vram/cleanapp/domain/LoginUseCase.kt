package com.vram.cleanapp.domain

import com.vram.cleanapp.domain.entity.UserToken
import com.vram.cleanapp.shared.data.Action

interface LoginUseCase {
    suspend fun login(userName: String?, password: String?) : Action<UserToken>
}

class LoginUseCaseImpl() : LoginUseCase {

    override suspend fun login(userName: String?, password: String?): Action<UserToken> {
        TODO()
    }

}