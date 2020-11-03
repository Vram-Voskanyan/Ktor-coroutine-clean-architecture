package com.vram.cleanapp.domain.repo

import com.vram.cleanapp.domain.entity.UserToken

interface LoginRepo {
    suspend fun login(email: String, password: String): UserToken
    suspend fun isEmailExist(email: String): Boolean
}