package com.vram.cleanapp.base

import com.vram.cleanapp.base.data.Action
import kotlinx.coroutines.coroutineScope

import java.lang.Exception

open class BaseRepo {

    protected suspend fun <T : Any> safeDBCall(call: suspend () -> T): Action<T> = coroutineScope {
        try {
            Action.Success(call())
        } catch (ex: Exception) {
            Action.Error(-1, ex)
        }
    }

}