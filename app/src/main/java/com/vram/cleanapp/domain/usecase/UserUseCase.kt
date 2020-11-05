package com.vram.cleanapp.domain.usecase

import com.vram.cleanapp.domain.common.BaseUseCase
import com.vram.cleanapp.domain.repo.UserRepo

interface UserUseCase {
    suspend fun userDetails()
    suspend fun userNotes()
}

class UserUseCaseImpl(
    private val userRepo: UserRepo
) : UserUseCase, BaseUseCase() {

    override suspend fun userDetails() {
        //TODO("Not yet implemented")
    }

    override suspend fun userNotes() {
        // check if user notes exist in cache.
        // get from network
        // save to cache.
        //TODO("Not yet implemented")
    }
}

