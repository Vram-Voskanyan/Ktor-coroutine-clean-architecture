package com.vram.cleanapp.domain.repo

import com.vram.cleanapp.domain.entity.UserNotes

interface NotesRepo {
    suspend fun userNotesFromNetwork(): UserNotes
    suspend fun userNotesFromCache(): UserNotes
    suspend fun saveNotes(userNotes: UserNotes)
    suspend fun removeNotes()
}