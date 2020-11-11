package com.vram.cleanapp.domain.entity

class UserNotes(val notes: List<Notes>)

class Notes(val id: String, val title: String, val description: String, val date: String)