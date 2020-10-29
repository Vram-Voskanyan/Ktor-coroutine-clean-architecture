package com.vram.cleanapp.shared.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vram.cleanapp.shared.data.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    // DATA_LOADED, LOADING
    private val _viewState: MutableLiveData<State> by lazy { MutableLiveData() }
    val viewState: LiveData<State> by lazy { _viewState }

    // For show Error popup
    private val _showError : MutableLiveData<Event<String>> by lazy { MutableLiveData() }
    val showError: LiveData<Event<String>> by lazy { _showError }

    // For show success Toast
    private val _showSuccess : MutableLiveData<Event<String>> by lazy { MutableLiveData() }
    val showSuccess: LiveData<Event<String>> by lazy { _showSuccess }

}

// Wrapped for Not testing purposes. For unit test we can change Dispatchers work making it synchronise:
// For Unit test:
// 1. add `private val testDispatcher = TestCoroutineDispatcher()`
// 2. set dispatchers: Dispatchers.setMain(testDispatcher)
// 3. For more info: Ask me, or just -> http://letmegooglethat.com/?q=unit+test+coroutines+android
// We wrap it in case of easy migration into custom Dispatchers, like Threads count increase needs.
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

