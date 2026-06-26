package com.pato.labo6.data.repository

import com.pato.labo6.data.model.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
}
