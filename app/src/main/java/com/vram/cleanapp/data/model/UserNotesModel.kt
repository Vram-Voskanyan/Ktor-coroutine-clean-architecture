package com.vram.cleanapp.data.model

import com.vram.cleanapp.domain.entity.UserNotes
import kotlinx.serialization.Serializable

@Serializable
data class UserNotesModel(val id: String, val title: String, val description: String)

fun UserNotesModel.toUserNotesEntity() = UserNotes(id, title, description)