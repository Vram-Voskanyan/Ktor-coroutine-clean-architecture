package com.vram.cleanapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vram.cleanapp.domain.usecase.LoginUseCase
import com.vram.cleanapp.domain.entity.LoginErrorTypes
import com.vram.cleanapp.domain.common.data.Action
import com.vram.cleanapp.view.core.BaseViewModel
import com.vram.cleanapp.view.core.Event
import com.vram.cleanapp.view.core.runOnBackground
import kotlinx.coroutines.delay

class MainViewModel(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    // TODO: profile LiveData
    // TODO: User Notes LiveData
    private val _onEmailError: MutableLiveData<Event<Any>> = MutableLiveData()
    val onEmailError: LiveData<Event<Any>> = _onEmailError

    fun login(email: String?, password: String?) = runOnBackground {
        startLoading()
        when (val response = loginUseCase.login(email, password)) {
            is Action.Success -> {
                showSuccess("Token: ${response.data.token}")
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


    // TODO: Mutable...
    override fun getErrorActionsMap(): MutableMap<Int, () -> Unit> = mutableMapOf(
        LoginErrorTypes.EMPTY_USERNAME.id to { showError("Email can't be empty") },
        LoginErrorTypes.EMPTY_PASSWORD.id to { showError("Password can't be empty") },
        LoginErrorTypes.INVALID_USERNAME.id to { showError("Email is invalid") },
        LoginErrorTypes.INVALID_PASSWORD.id to { showError("Password is invalid") },
        LoginErrorTypes.NO_SUCH_USERNAME.id to ::noSuchUser
    )

    private fun noSuchUser() {
        showError("There is no such User Email in App.")
        _onEmailError.postValue(Event(Unit))
    }

}