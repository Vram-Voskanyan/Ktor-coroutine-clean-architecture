package com.vram.cleanapp.data.model

import kotlinx.serialization.Serializable

// No need for `data` class.
@Serializable
class EmailCheckerModel(val isEmailExist: Boolean)

fun EmailCheckerModel.toBoolean() = isEmailExist