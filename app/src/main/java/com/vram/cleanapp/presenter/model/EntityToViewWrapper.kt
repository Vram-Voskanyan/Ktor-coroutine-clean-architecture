package com.vram.cleanapp.presenter.model

import com.vram.cleanapp.domain.entity.UserInfo
import com.vram.cleanapp.domain.entity.UserNotes

suspend fun UserNotes.toViewData(timeStampToDate: suspend (String) -> String): String {
    var userNotesAsString = ""
    notes.forEach {
        userNotesAsString += "id: ${it.id} | ${it.title} | ${timeStampToDate(it.date)}\n"
    }
    return userNotesAsString
}

suspend fun UserInfo.toViewData(): String = "id: $id | name: $name | age: $age"


