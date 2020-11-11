package com.vram.cleanapp.domain.repo

import com.vram.cleanapp.domain.entity.UserNotes

interface UserRepo {
    suspend fun saveToken(token: String)
    suspend fun removeToken()
    suspend fun userDetailsFromNetwork() // TODO: Naming

    // TODO move to Notes repo
    suspend fun userNotesFromNetwork(): UserNotes
    suspend fun userNotesFromCache(): UserNotes
    suspend fun saveNotes(userNotes: UserNotes)
    suspend fun removeNotes()
}