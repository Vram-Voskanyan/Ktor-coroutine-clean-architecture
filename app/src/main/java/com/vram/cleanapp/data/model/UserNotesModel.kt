package com.vram.cleanapp.data.model

import com.vram.cleanapp.domain.entity.Notes
import com.vram.cleanapp.domain.entity.UserNotes
import kotlinx.serialization.Serializable

@Serializable
class UserNotesModel(val notes: List<NotesModel>) {
    constructor(userNotes: UserNotes) : this(userNotes.notes.map {
        NotesModel(
            it.id,
            it.title,
            it.description,
            it.date
        )
    })
}

@Serializable
class NotesModel(val id: String, val title: String, val description: String, val date: String)

fun UserNotesModel.toUserNotesEntity() = UserNotes(
    notes.map {
        Notes(it.id, it.title, it.description, it.date)
    }
)