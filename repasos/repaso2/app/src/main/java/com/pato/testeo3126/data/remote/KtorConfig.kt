package com.pato.testeo3126.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object KtorClient {

    const val BASE_URL = "http://192.168.0.150:3005"

    val client = HttpClient(OkHttp) {

        // Parseo automático de JSON
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

        // Configuración para todas las peticiones
        defaultRequest {
            url(BASE_URL)
            header(HttpHeaders.Accept, "application/json")
        }
    }
}