package com.vram.cleanapp.domain.usecase

import com.vram.cleanapp.domain.entity.LoginErrorTypes
import com.vram.cleanapp.domain.entity.UserToken
import com.vram.cleanapp.domain.repo.ValidationRepo
import com.vram.cleanapp.domain.common.usecase.BaseUseCase
import com.vram.cleanapp.domain.common.data.Result
import com.vram.cleanapp.domain.common.threading.onDefaultAsync
import com.vram.cleanapp.domain.common.threading.onIOAsync
import com.vram.cleanapp.domain.entity.toException
import com.vram.cleanapp.domain.repo.LoginRepo
import com.vram.cleanapp.domain.repo.UserRepo

interface LoginUseCase {
    suspend fun login(email: String?, password: String?): Result<UserToken>
}

class LoginUseCaseImpl(
    private val validationRepo: ValidationRepo,
    private val loginRepo: LoginRepo,
    private val userRepo: UserRepo
) : LoginUseCase, BaseUseCase() {

    override suspend fun login(email: String?, password: String?): Result<UserToken> = safeCall {
        val nonNullUserName = onIOAsync { checkValidEmail(email) }
        val nonNullPassword = onDefaultAsync { validatePassword(password) }
        val loginResponse = loginRepo.login(nonNullUserName.await(), nonNullPassword.await())
        userRepo.saveToken(loginResponse.token)
        loginResponse
    }

    private suspend fun checkValidEmail(email: String?): String {
        val nonNullEmail = localEmailValidation(email)
        networkEmailValidation(nonNullEmail)
        return nonNullEmail
    }

    private fun localEmailValidation(email: String?): String {
        if (email.isNullOrEmpty()) throw LoginErrorTypes.EMPTY_USERNAME.toException()
        if (!validationRepo.isValidUserEmail(email)) throw LoginErrorTypes.INVALID_USERNAME.toException()
        return email
    }

    private suspend fun networkEmailValidation(email: String) {
        if (!loginRepo.isEmailExist(email)) throw LoginErrorTypes.NO_SUCH_USERNAME.toException()
    }

    private fun validatePassword(password: String?): String {
        if (password.isNullOrEmpty()) throw LoginErrorTypes.EMPTY_PASSWORD.toException()
        // NO need on Login call. this is just example.
        //if (!validationRepo.isValidUserPassword(password)) throw BaseException(LoginErrorTypes.INVALID_PASSWORD.id)
        return password
    }

}

