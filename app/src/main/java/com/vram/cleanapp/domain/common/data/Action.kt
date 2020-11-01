package com.vram.cleanapp.domain.common.data

// TODO: change name to Result?
sealed class Action<out R> {

    data class Success<out T>(val data: T) : Action<T>()
    data class Error(val exception: Exception, val extraErrorCode: Int = -1) : Action<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}