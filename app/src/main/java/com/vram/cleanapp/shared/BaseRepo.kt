package com.vram.cleanapp.shared

import com.vram.cleanapp.shared.data.Action
import kotlinx.coroutines.coroutineScope

import java.lang.Exception

open class BaseRepo {

    protected suspend fun <T : Any> safeDBCall(call: suspend () -> T): Action<T> = coroutineScope {
        try {
            Action.Success(call())
        } catch (ex: Exception) {
            Action.Error(ex)
        }
    }

}