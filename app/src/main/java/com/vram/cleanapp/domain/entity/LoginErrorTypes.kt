package com.vram.cleanapp.domain.entity

enum class LoginErrorTypes(val id: Int) {
    EMPTY_USERNAME(1),
    EMPTY_PASSWORD(2),
    INVALID_USERNAME(3),
    INVALID_PASSWORD(4),
    NO_SUCH_USERNAME(5)
}
