package com.vram.cleanapp.data.model

import com.vram.cleanapp.domain.entity.UserInfo
import kotlinx.serialization.Serializable

// No need for `data` class.
@Serializable
class UserInfoModel(val id: String, val name: String, val age: Int)

fun UserInfoModel.toUserInfoEntity() = UserInfo(id, name, age)