package com.vram.cleanapp.view

import com.vram.cleanapp.domain.usecase.LoginUseCase
import com.vram.cleanapp.domain.entity.LoginErrorTypes
import com.vram.cleanapp.domain.common.data.Action
import com.vram.cleanapp.view.core.BaseViewModel
import com.vram.cleanapp.view.core.runOnBackground
import kotlinx.coroutines.delay

class MainViewModel(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    // TODO: Mutable...
    override fun getErrorActionsMap(): MutableMap<Int, () -> Unit> = mutableMapOf(
        LoginErrorTypes.EMPTY_USERNAME.id to { showError("Email can't be empty") },
        LoginErrorTypes.EMPTY_PASSWORD.id to { showError("Password can't be empty") },
        LoginErrorTypes.INVALID_USERNAME.id to { showError("Email is invalid") },
        LoginErrorTypes.INVALID_PASSWORD.id to { showError("Password is invalid") },
        LoginErrorTypes.NO_SUCH_USERNAME.id to { showError("There is no such User Email in App.") }
    )

    // TODO: profile LiveData
    // TODO: User Notes LiveData

    fun login(email: String?, password: String?) = runOnBackground {
        startLoading()
        delay(1000) // TODO: REMOVE.
        when (val response = loginUseCase.login(email, password)) {
            is Action.Success -> {
                TODO()
                // TODO set token
                // TODO get profile live data
                // TODO get notes list.
            }
            is Action.Error -> {
                handleError(response.extraErrorCode)
            }
        }
        dataReceived()
    }


}