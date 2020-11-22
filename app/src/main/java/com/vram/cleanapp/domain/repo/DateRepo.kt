package com.vram.cleanapp.domain.repo

interface DateRepo {
    suspend fun timeStampToDate(date: String, pattern: String): String
}