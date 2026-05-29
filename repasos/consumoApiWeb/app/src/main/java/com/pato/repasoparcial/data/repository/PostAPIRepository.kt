package com.pato.repasoparcial.data.repository

import com.pato.repasoparcial.data.dto.PostDTO
import com.pato.repasoparcial.data.dto.toModel
import com.pato.repasoparcial.data.model.Post
import com.pato.repasoparcial.data.remote.JsonAPI
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PostAPIRepository(private val client: HttpClient) : JsonAPI {

    override suspend fun getJsonData(): List<Post> {
        val response: List<PostDTO> = client.get("").body()
        return response.map { it.toModel() }
    }

    override suspend fun postJsonData(post: Post): Post {
        val postDto = PostDTO(
            userId = post.userId,
            id = post.id,
            title = post.title,
            body = post.body
        )
        val response = client.post("") {
            contentType(ContentType.Application.Json)
            setBody(postDto)
        }
        return response.body<PostDTO>().toModel()
    }
}
