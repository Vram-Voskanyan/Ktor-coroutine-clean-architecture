package com.vram.cleanapp.presenter.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vram.cleanapp.domain.common.data.*

abstract class BaseViewModel : ViewModel() {

    private val loginErrorsMap: Map<Int, () -> Unit> by lazy { initErrorMap() }

    abstract fun getErrorActionsMap(): Map<Int, () -> Unit>

    private fun initErrorMap(): Map<Int, () -> Unit> =
        getErrorActionsMap().toMutableMap().apply {
            putIfAbsent(BAD_REQUEST) { showError("Client side issue") }
            putIfAbsent(INTERNAL_SERVER_ERROR) { showError("Server issue") }
            putIfAbsent(NO_INTERNET) { showError("Please check Network connection") }
            putIfAbsent(UNAUTHORIZED) { showError("Request is Unauthorized") }
            putIfAbsent(TODO_EXCEPTION) { showError("Ups, it is not implemented yet. Please be patient :]") }
        }

    // DATA_LOADED, LOADING
    private val _viewState: MutableLiveData<ViewState> by lazy { MutableLiveData<ViewState>() }
    val viewState: LiveData<ViewState> by lazy { _viewState }

    // For show Error popup
    private val _showError: MutableLiveData<Event<String>> by lazy { MutableLiveData<Event<String>>() }
    val showError: LiveData<Event<String>> by lazy { _showError }

    // For show success Toast
    private val _showSuccess: MutableLiveData<Event<String>> by lazy { MutableLiveData<Event<String>>() }
    val showSuccess: LiveData<Event<String>> by lazy { _showSuccess }

    protected fun startLoading() {
        _viewState.postValue(ViewState.LOADING)
    }

    protected fun dataReceived() {
        _viewState.postValue(ViewState.DATA_LOADED)
    }

    protected fun showSuccess(message: String) {
        _showSuccess.postValue(Event(message))
    }

    protected fun showError(message: String) {
        _showError.postValue(Event(message))
    }

    fun handleError(errorId: Int) {
        loginErrorsMap[errorId]?.invoke() ?: showError("Unknown error: $errorId")
    }

    suspend fun <T> Result<T>.resultDefaultHandle(successBlock: suspend (T) -> Unit) {
        // start loading? - NO
        // I prefer to handle loading, or some ui actions on each call.
        when (this) {
            is Result.Success -> successBlock(data)
            is Result.Error -> handleError(extraErrorCode)
        }
        // end loading? - NO
    }

}