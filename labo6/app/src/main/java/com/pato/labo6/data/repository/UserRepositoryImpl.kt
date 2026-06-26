package com.pato.labo6.data.repository

import com.pato.labo6.data.api.KtorClient
import com.pato.labo6.data.api.Users.UserDto
import com.pato.labo6.data.api.Users.toModel
import com.pato.labo6.data.model.User
import io.ktor.client.call.body
import io.ktor.client.request.get


class UserRepositoryImpl : UserRepository {
    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val users = KtorClient.client
                .get("https://jsonplaceholder.typicode.com/users")
                .body<List<UserDto>>()
                .map { it.toModel() }
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}