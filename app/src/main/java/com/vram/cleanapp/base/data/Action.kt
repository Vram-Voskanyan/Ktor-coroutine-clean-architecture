package com.vram.cleanapp.base.data

sealed class Action<out R> {

    data class Success<out T>(val data: T) : Action<T>()
    data class Error(val errorCode: Int = -1, val exception: Exception) : Action<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}