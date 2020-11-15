package com.vram.cleanapp.domain.entity

data class UserNotes(val notes: List<Notes>)

data class Notes(val id: String, val title: String, val description: String, val date: String)