package com.vram.cleanapp.domain.common.usecase

import com.vram.cleanapp.domain.common.data.Result
import com.vram.cleanapp.domain.common.data.BaseException
import com.vram.cleanapp.domain.common.data.SAFE_CALL_FAIL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

open class BaseUseCase {

    // no need for supervisorScope!!!
    suspend fun <T : Any> safeCall(call: suspend CoroutineScope.() -> T): Result<T> = try {
        coroutineScope {
            Result.Success(call())
        }
    } catch (baseException: BaseException) {
        Result.Error(baseException, baseException.extraErrorCode)
    } catch (ex: Exception) {
        Result.Error(ex, SAFE_CALL_FAIL)
    }

}