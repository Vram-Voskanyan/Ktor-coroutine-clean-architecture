package com.vram.cleanapp.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vram.cleanapp.domain.common.data.UNAUTHORIZED
import com.vram.cleanapp.domain.entity.LoginErrorTypes.*
import com.vram.cleanapp.domain.entity.UserToken
import com.vram.cleanapp.domain.usecase.LoginUseCase
import com.vram.cleanapp.domain.usecase.UserUseCase
import com.vram.cleanapp.presenter.core.BaseViewModel
import com.vram.cleanapp.presenter.core.Event
import com.vram.cleanapp.presenter.core.runOnBackground
import com.vram.cleanapp.presenter.model.toViewData

class MainViewModel(
    private val loginUseCase: LoginUseCase,
    private val userUseCase: UserUseCase
) : BaseViewModel() {

    private val _onEmailError: MutableLiveData<Event<Any>> by lazy { MutableLiveData() }
    val onEmailError: LiveData<Event<Any>> by lazy { _onEmailError }

    private val _userNotes: MutableLiveData<String> by lazy { MutableLiveData() }
    val userNotes: LiveData<String> by lazy { _userNotes }

    private val _userInfo: MutableLiveData<String> by lazy { MutableLiveData() }
    val userInfo: LiveData<String> by lazy { _userInfo }

    private val _userNotesLoadingShow: MutableLiveData<Event<Any>> by lazy { MutableLiveData() }
    val userNotesLoadingShow: LiveData<Event<Any>> by lazy { _userNotesLoadingShow }

    private val _userNotesLoadingHide: MutableLiveData<Event<Any>> by lazy { MutableLiveData() }
    val userNotesLoadingHide: LiveData<Event<Any>> by lazy { _userNotesLoadingHide }

    private val _userInfoLoadingShow: MutableLiveData<Event<Any>> by lazy { MutableLiveData() }
    val userInfoLoadingShow: LiveData<Event<Any>> by lazy { _userInfoLoadingShow }

    private val _userInfoLoadingHide: MutableLiveData<Event<Any>> by lazy { MutableLiveData() }
    val userInfoLoadingHide: LiveData<Event<Any>> by lazy { _userInfoLoadingHide }

    private val _onPasswordError: MutableLiveData<Event<Any>> by lazy { MutableLiveData() }
    val onPasswordError: LiveData<Event<Any>> by lazy { _onPasswordError }

    fun login(email: String?, password: String?) = runOnBackground {
        startLoading()
        loginUseCase.login(email, password).resultDefaultHandle {
            onLoginSuccess(it)
        }
        dataReceived()
    }

    fun resetAll() = runOnBackground {
        userUseCase.removeNotesFromCache()
    }

    private fun onLoginSuccess(userToken: UserToken) {
        showSuccess("Token: ${userToken.token}")
        requestUserDetails()
        requestUserNotes()
    }

    // Do we have coroutine leaks here? caller also is on Background
    private fun requestUserNotes() = runOnBackground {
        _userNotesLoadingShow.postValue(Event(Any()))
        userUseCase.userNotes().resultDefaultHandle {
            _userNotes.postValue(it.toViewData { timeStamp ->
                userUseCase.userAgeToDate(timeStamp)
            })
        }
        _userNotesLoadingHide.postValue(Event(Any()))
    }

    private fun requestUserDetails() = runOnBackground {
        _userInfoLoadingShow.postValue(Event(Any()))
        userUseCase.userDetails().resultDefaultHandle {
            _userInfo.postValue(it.toViewData())
        }
        _userInfoLoadingHide.postValue(Event(Any()))

    }

    override fun getErrorActionsMap(): Map<Int, () -> Unit> = mapOf(
        EMPTY_USERNAME.id to { showError("Email can't be empty") }, // TODO: move to string.xml
        EMPTY_PASSWORD.id to { showError("Password can't be empty") },
        INVALID_USERNAME.id to { showError("Email is invalid") },
        INVALID_PASSWORD.id to { showError("Password is invalid") },
        NO_SUCH_USERNAME.id to ::noSuchUser,
        // this overrides base.
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