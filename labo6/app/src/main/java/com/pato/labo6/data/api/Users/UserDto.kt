package com.pato.labo6.data.api.Users

import com.pato.labo6.data.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val email: String
)

fun UserDto.toModel(): User {
    return User(
        id = id,
        name = name,
        email = email
    )
}