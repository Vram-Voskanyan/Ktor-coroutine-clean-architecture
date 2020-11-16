package com.vram.cleanapp.presenter.model

import com.vram.cleanapp.domain.entity.UserInfo
import com.vram.cleanapp.domain.entity.UserNotes
import com.vram.cleanapp.presenter.core.getDateFromTimeStamp

fun UserNotes.toViewData(): String {
    var userNotesAsString = ""
    notes.forEach {
        userNotesAsString += "id: ${it.id} | ${it.title} | ${getDateFromTimeStamp(it.date)}\n"
    }
    return userNotesAsString
}

fun UserInfo.toViewData(): String = "id: $id | name: $name | age: $age"


