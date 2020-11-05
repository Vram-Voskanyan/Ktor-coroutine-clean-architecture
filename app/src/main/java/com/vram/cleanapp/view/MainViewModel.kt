package com.vram.cleanapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vram.cleanapp.domain.usecase.LoginUseCase
import com.vram.cleanapp.domain.entity.LoginErrorTypes.*
import com.vram.cleanapp.domain.common.data.Action
import com.vram.cleanapp.domain.common.data.UNAUTHORIZED
import com.vram.cleanapp.domain.entity.UserToken
import com.vram.cleanapp.domain.usecase.UserUseCase
import com.vram.cleanapp.view.core.BaseViewModel
import com.vram.cleanapp.view.core.Event
import com.vram.cleanapp.view.core.runOnBackground

class MainViewModel(
    private val loginUseCase: LoginUseCase,
    private val userUseCase: UserUseCase
) : BaseViewModel() {

    // TODO: profile LiveData
    // TODO: User Notes LiveData
    private val _onEmailError: MutableLiveData<Event<Any>> = MutableLiveData()
    val onEmailError: LiveData<Event<Any>> = _onEmailError

    private val _onPasswordError: MutableLiveData<Event<Any>> = MutableLiveData()
    val onPasswordError: LiveData<Event<Any>> = _onPasswordError

    fun login(email: String?, password: String?) = runOnBackground {
        startLoading()
        when (val response = loginUseCase.login(email, password)) {
            is Action.Success -> onLoginSuccess(response.data)
            is Action.Error -> handleError(response.extraErrorCode)
        }
        dataReceived()
    }

    private fun onLoginSuccess(userToken: UserToken) {
        showSuccess("Token: ${userToken.token}")
        requestUserDetails()
        requestUserNotes()
    }

    // Do we have coroutine leaks here? caller also is on Background
    private fun requestUserNotes() = runOnBackground {
        userUseCase.userNotes()
    }

    private fun requestUserDetails() = runOnBackground {
        userUseCase.userDetails()
    }

    // TODO: Mutable...
    override fun getErrorActionsMap(): MutableMap<Int, () -> Unit> = mutableMapOf(
        EMPTY_USERNAME.id to { showError("Email can't be empty") },
        EMPTY_PASSWORD.id to { showError("Password can't be empty") },
        INVALID_USERNAME.id to { showError("Email is invalid") },
        INVALID_PASSWORD.id to { showError("Password is invalid") },
        NO_SUCH_USERNAME.id to ::noSuchUser,
        // this is overrides base.
        UNAUTHORIZED to ::showInvalidPassword
    )

    private fun showInvalidPassword() {
        showError("Invalid user name or password.")
        _onPasswordError.postValue(Event(Unit))
    }

    private fun noSuchUser() {
        showError("There is no such User Email in App.")
        _onEmailError.postValue(Event(Unit))
    }

}