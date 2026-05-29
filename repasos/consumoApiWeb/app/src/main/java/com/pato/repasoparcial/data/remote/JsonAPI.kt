package com.pato.repasoparcial.data.remote

import com.pato.repasoparcial.data.model.Post

interface JsonAPI {
    suspend fun getJsonData(): List<Post>
    suspend fun postJsonData(post: Post): Post
}