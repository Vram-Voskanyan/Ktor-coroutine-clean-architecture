package com.vram.cleanapp.data.repo

import com.vram.cleanapp.domain.common.data.BaseRepo
import com.vram.cleanapp.domain.repo.DateRepo
import com.vram.cleanapp.service.convertToDateString

class DateRepoImpl : DateRepo, BaseRepo() {

    override suspend fun timeStampToDate(date: String, pattern: String): String =
        convertToDateString(date, pattern)
}