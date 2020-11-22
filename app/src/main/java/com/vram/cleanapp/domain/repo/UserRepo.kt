package com.vram.cleanapp.domain.repo

import com.vram.cleanapp.domain.entity.UserInfo

interface UserRepo {
    suspend fun saveToken(token: String)
    suspend fun removeToken()
    suspend fun userDetailsFromNetwork(): UserInfo // TODO: Naming
}