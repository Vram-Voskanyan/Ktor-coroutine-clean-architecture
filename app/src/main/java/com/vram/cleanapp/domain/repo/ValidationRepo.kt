package com.vram.cleanapp.domain.repo

interface ValidationRepo {
    fun isValidUserEmail(email: String): Boolean
    fun isValidUserPassword(password: String): Boolean
}