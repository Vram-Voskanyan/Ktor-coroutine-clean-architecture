package com.vram.cleanapp.domain.usecase

import com.vram.cleanapp.domain.common.BaseUseCase
import com.vram.cleanapp.domain.common.data.*
import com.vram.cleanapp.domain.common.onIOLaunch
import com.vram.cleanapp.domain.entity.UserInfo
import com.vram.cleanapp.domain.entity.UserNotes
import com.vram.cleanapp.domain.repo.UserRepo

interface UserUseCase {
    suspend fun userDetails(): Action<UserInfo>
    suspend fun removeNotesFromCache()
    suspend fun userNotes(): Action<UserNotes>
}

class UserUseCaseImpl(
    private val userRepo: UserRepo
) : UserUseCase, BaseUseCase() {

    override suspend fun userDetails() = safeCall {
        userRepo.userDetailsFromNetwork()
    }

    override suspend fun userNotes() = safeCall {
        // check if user notes exist in cache.
        notesFromCache()?.let { return@safeCall it }
        // get from network
        val serverNotes: UserNotes = userRepo.userNotesFromNetwork()
        // save into cache
        onIOLaunch { userRepo.saveNotes(serverNotes) }
        serverNotes
    }

    override suspend fun removeNotesFromCache() {
        safeCall { onIOLaunch { userRepo.removeNotes() } }
    }

    private suspend fun notesFromCache(): UserNotes? =
        when (val cacheResult = safeCall { userRepo.userNotesFromCache() }) {
            is Action.Success -> cacheResult.data
            is Action.Error -> {
                if (cacheResult.extraErrorCode == ON_SUCH_ELEMENT) null
                else throw UnknownIOException()
            }
            // todo: need to have separate exception, not same as in else...
            else -> throw UnknownIOException()
        }

}


