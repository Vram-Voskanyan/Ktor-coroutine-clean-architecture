package com.vram.cleanapp.domain.usecase

import com.vram.cleanapp.domain.common.BaseUseCase
import com.vram.cleanapp.domain.common.data.Action
import com.vram.cleanapp.domain.common.data.ON_SUCH_ELEMENT
import com.vram.cleanapp.domain.common.data.UnknownIOException
import com.vram.cleanapp.domain.common.data.todoCrash
import com.vram.cleanapp.domain.entity.UserNotes
import com.vram.cleanapp.domain.repo.UserRepo

interface UserUseCase {
    suspend fun userDetails()
    suspend fun userNotes(): Action<UserNotes>
}

class UserUseCaseImpl(
    private val userRepo: UserRepo
) : UserUseCase, BaseUseCase() {

    override suspend fun userDetails() {
        //TODO("Not yet implemented")
    }

    override suspend fun userNotes() = safeCall {
        // check if user notes exist in cache.
        val cacheNotes: UserNotes? = notesFromCache()
        if (cacheNotes != null) return@safeCall cacheNotes
        val serverNotes: UserNotes = userRepo.userNotesFromNetwork()
        todoCrash()
        // get from network
        // save to cache.
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


