package com.vram.cleanapp.data.model

import com.vram.cleanapp.domain.entity.UserToken
import kotlinx.serialization.Serializable

// No need for `data` class.
@Serializable
class UserTokenModel(val token: String)

fun UserTokenModel.toUserTokenEntity() = UserToken(token)