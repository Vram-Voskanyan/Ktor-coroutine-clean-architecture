package com.vram.cleanapp.domain.usecase

import com.vram.cleanapp.domain.entity.LoginErrorTypes
import com.vram.cleanapp.domain.entity.UserToken
import com.vram.cleanapp.domain.repo.ValidationRepo
import com.vram.cleanapp.domain.common.BaseUseCase
import com.vram.cleanapp.domain.common.data.Action
import com.vram.cleanapp.domain.common.data.BaseException
import com.vram.cleanapp.domain.common.onDefaultAsync
import com.vram.cleanapp.domain.common.onIOAsync
import com.vram.cleanapp.domain.repo.LoginRepo
import kotlinx.coroutines.awaitAll

interface LoginUseCase {
    suspend fun login(email: String?, password: String?): Action<UserToken>
}

class LoginUseCaseImpl(
    private val validationRepo: ValidationRepo,
    private val loginRepo: LoginRepo
) : LoginUseCase, BaseUseCase() {

    override suspend fun login(email: String?, password: String?): Action<UserToken> = safeCall {
        val nonNullUserName = onIOAsync { checkValidEmail(email) }
        val nonNullPassword = onDefaultAsync { validatePassword(password) }
        loginRepo.login(nonNullUserName.await(), nonNullPassword.await())
    }

    private suspend fun checkValidEmail(email: String?): String {
        val nonNullEmail = localEmailValidation(email)
        networkEmailValidation(nonNullEmail)
        return nonNullEmail
    }

    private fun localEmailValidation(email: String?): String {
        if (email.isNullOrEmpty()) throw BaseException(LoginErrorTypes.EMPTY_USERNAME.id)
        if (!validationRepo.isValidUserEmail(email)) throw BaseException(LoginErrorTypes.INVALID_USERNAME.id)
        return email
    }

    private suspend fun networkEmailValidation(email: String) {
        if (!loginRepo.isEmailExist(email)) throw BaseException(LoginErrorTypes.NO_SUCH_USERNAME.id)
    }

    private fun validatePassword(password: String?): String {
        if (password.isNullOrEmpty()) throw BaseException(LoginErrorTypes.EMPTY_PASSWORD.id)
        // NO need on Login call. this is just example.
        //if (!validationRepo.isValidUserPassword(password)) throw BaseException(LoginErrorTypes.INVALID_PASSWORD.id)
        return password
    }

}

