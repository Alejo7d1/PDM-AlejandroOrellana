package com.pato.repasoparcial.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorConfig {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/posts"

    val client = HttpClient(OkHttp) {

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

        // peticion
        defaultRequest {
            url(BASE_URL)
            header(HttpHeaders.Accept, "application/json")
        }
    }
}