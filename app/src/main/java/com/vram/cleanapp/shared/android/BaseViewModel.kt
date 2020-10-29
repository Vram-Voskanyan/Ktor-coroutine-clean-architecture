package com.vram.cleanapp.shared.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vram.cleanapp.shared.data.Event

abstract class BaseViewModel : ViewModel() {

    // DATA_LOADED, LOADING
    private val _viewState: MutableLiveData<ViewState> by lazy { MutableLiveData() }
    val viewState: LiveData<ViewState> by lazy { _viewState }

    // For show Error popup
    private val _showError: MutableLiveData<Event<String>> by lazy { MutableLiveData() }
    val showError: LiveData<Event<String>> by lazy { _showError }

    // For show success Toast
    private val _showSuccess: MutableLiveData<Event<String>> by lazy { MutableLiveData() }
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
}