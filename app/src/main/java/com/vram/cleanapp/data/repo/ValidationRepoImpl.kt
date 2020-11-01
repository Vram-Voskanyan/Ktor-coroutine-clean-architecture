package com.vram.cleanapp.data.repo

import com.vram.cleanapp.data.service.isValidEmail
import com.vram.cleanapp.data.service.isValidPassword
import com.vram.cleanapp.domain.repo.ValidationRepo

class ValidationRepoImpl : ValidationRepo {
    override fun isValidUserEmail(email: String): Boolean = isValidEmail(email)
    override fun isValidUserPassword(password: String): Boolean = isValidPassword(password)
}