package com.vram.cleanapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vram.cleanapp.base.data.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _viewState: MutableLiveData<State> by lazy { MutableLiveData() }
    val viewState: LiveData<State> by lazy { _viewState }

    private val _showError : MutableLiveData<Event<String>> by lazy { MutableLiveData() }
    val showError: LiveData<Event<String>> by lazy { _showError }

    private val _showSuccess : MutableLiveData<Event<String>> by lazy { MutableLiveData() }
    val showSuccess: LiveData<Event<String>> by lazy { _showSuccess }

}

fun ViewModel.runOnBackground(block: suspend () -> Unit): Job =
    viewModelScope.launch(Dispatchers.Default) {
        block()
    }

fun ViewModel.runOnIO(block: suspend () -> Unit): Job = viewModelScope.launch(Dispatchers.IO) {
    block()
}

enum class State {
    DATA_LOADED, LOADING
}

