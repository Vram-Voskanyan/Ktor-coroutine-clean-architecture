package com.vram.cleanapp.domain.common.data

open class BaseException(val extraErrorCode: Int) : Exception()

const val BAD_REQUEST: Int = 400
const val UNAUTHORIZED: Int = 401
const val INTERNAL_SERVER_ERROR: Int = 500
const val NO_INTERNET: Int = 666
const val UNKNOWN: Int = -1

class BadRequest : BaseException(BAD_REQUEST)
class Unauthorized : BaseException(UNAUTHORIZED)
class InternalServerError : BaseException(INTERNAL_SERVER_ERROR)
class NoInternet : BaseException(NO_INTERNET) // (:
class Unknown : BaseException(UNKNOWN)