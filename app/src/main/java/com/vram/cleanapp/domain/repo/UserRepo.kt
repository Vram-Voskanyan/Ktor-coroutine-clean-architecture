package com.vram.cleanapp.domain.repo

interface UserRepo {
    suspend fun saveToken(token: String)
    suspend fun removeToken()
    suspend fun userDetailsFromNetwork() // TODO: Naming
    suspend fun userNotesFromNetwork()
    suspend fun userNotesFromCache()
}