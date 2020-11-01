package com.vram.cleanapp.domain.repo

import com.vram.cleanapp.data.service.EmailChecker

interface LoginRepo {
    suspend fun login(email: String, password: String)
    suspend fun isEmailExist(email: String): EmailChecker
}