package com.vram.cleanapp.domain.common

import com.vram.cleanapp.domain.common.data.Action
import com.vram.cleanapp.domain.common.data.BaseException
import com.vram.cleanapp.domain.common.data.SAFE_CALL_FAIL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

open class BaseUseCase {

    // no need for supervisorScope!!!
    suspend fun <T : Any> safeCall(call: suspend CoroutineScope.() -> T): Action<T> = try {
        coroutineScope {
            Action.Success(call())
        }
    } catch (baseException: BaseException) {
        Action.Error(baseException, baseException.extraErrorCode)
    } catch (ex: Exception) {
        Action.Error(ex, SAFE_CALL_FAIL)
    }

}