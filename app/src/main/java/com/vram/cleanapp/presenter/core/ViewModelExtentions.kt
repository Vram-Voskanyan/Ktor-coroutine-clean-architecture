package com.vram.cleanapp.presenter.core

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

// Wrapped for Not testing purposes. For unit test we can change Dispatchers work making it synchronise:
// For Unit test:
// 1. add `private val testDispatcher = TestCoroutineDispatcher()`
// 2. set dispatchers: Dispatchers.setMain(testDispatcher)
// 3. For more info: Ask me, or just -> http://letmegooglethat.com/?q=unit+test+coroutines+android
// We wrap it in case of easy migration into custom Dispatchers, like Threads count increase needs.
fun BaseViewModel.runOnBackground(block: suspend () -> Unit): Job =
    viewModelScope.launch(Dispatchers.Default) {
        block()
    }

fun BaseViewModel.runOnIO(block: suspend () -> Unit): Job =
    viewModelScope.launch(Dispatchers.IO) {
        block()
    }