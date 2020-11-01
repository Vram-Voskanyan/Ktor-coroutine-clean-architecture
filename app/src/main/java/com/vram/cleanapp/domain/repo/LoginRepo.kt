package com.vram.cleanapp.domain.repo

interface LoginRepo {
    fun login(email: String, password: String)
    fun isEmailExist(email: String): Boolean
}