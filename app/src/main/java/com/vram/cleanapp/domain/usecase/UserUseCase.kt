package com.vram.cleanapp.domain.usecase

import com.vram.cleanapp.domain.common.usecase.BaseUseCase
import com.vram.cleanapp.domain.common.data.*
import com.vram.cleanapp.domain.common.threading.onIOLaunch
import com.vram.cleanapp.domain.entity.UserInfo
import com.vram.cleanapp.domain.entity.UserNotes
import com.vram.cleanapp.domain.repo.DateRepo
import com.vram.cleanapp.domain.repo.NotesRepo
import com.vram.cleanapp.domain.repo.UserRepo

interface UserUseCase {
    suspend fun userDetails(): Result<UserInfo>
    suspend fun removeNotesFromCache()
    suspend fun userNotes(): Result<UserNotes>
    suspend fun userAgeToDate(timestamp: String): String
}

class UserUseCaseImpl(
    private val userRepo: UserRepo,
    private val notesRepo: NotesRepo,
    private val dateRepo: DateRepo
) : UserUseCase, BaseUseCase() {

    override suspend fun userDetails() = safeCall {
        userRepo.userDetailsFromNetwork()
    }

    override suspend fun userNotes() = safeCall {
        // check if user notes exist in cache.
        notesFromCache()?.let { return@safeCall it }
        // get from network
        val serverNotes: UserNotes = notesRepo.userNotesFromNetwork()
        // save into cache
        onIOLaunch { notesRepo.saveNotes(serverNotes) }
        serverNotes
    }

    override suspend fun userAgeToDate(timestamp: String): String =
        dateRepo.timeStampToDate(timestamp, DATE_PATTERN)


    override suspend fun removeNotesFromCache() {
        safeCall { onIOLaunch { notesRepo.removeNotes() } }
    }

    private suspend fun notesFromCache(): UserNotes? =
        when (val cacheResult = safeCall { notesRepo.userNotesFromCache() }) {
            is Result.Success -> cacheResult.data
            is Result.Error -> {
                if (cacheResult.extraErrorCode == ON_SUCH_ELEMENT) null
                else throw UnknownIOException()
            }
        }

}


