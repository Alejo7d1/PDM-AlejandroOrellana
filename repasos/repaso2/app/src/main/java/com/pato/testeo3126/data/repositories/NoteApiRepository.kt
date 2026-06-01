package com.pato.testeo3126.data.repositories

import android.util.Log
import io.ktor.http.ContentType
import com.pato.testeo3126.data.dto.NoteDTO
import com.pato.testeo3126.data.dto.PostNoteDTO
import com.pato.testeo3126.data.dto.toModel
import com.pato.testeo3126.data.model.Note
import com.pato.testeo3126.data.remote.NoteAPI
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.contentType

class NoteApiRepository(private val httpClient: HttpClient) : NoteAPI{
    override suspend fun getNotes(): List<Note> {
        try {
            val response: List<NoteDTO> = httpClient.get("/notes").body()
            return response.map {it.toModel() }
        } catch (e: Exception) {
            Log.e("NoteRepository", "Error al obtener notas: ${e.message}")
            throw e
        }
    }

    override suspend fun getNotebyId(id: Int): Note {
        try {
            val response: NoteDTO = httpClient.get("/notes/$id").body()
            return response.toModel()
        } catch (e: Exception) {
            Log.e("NoteRepository", "Error al obtener nota $id: ${e.message}")
            throw e
        }
    }

    override suspend fun postNote(title: String, body: String, author: String): Result<NoteDTO> {
        try{
            val request = PostNoteDTO(
                title = title,
                body = body,
                author = author
            )
            val response: NoteDTO = httpClient.post ("/notes"){
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
            return Result.success(response)
        } catch (e: Exception) {
            Log.e("PostRepository", "Error al crear post: ${e.message}")
            return Result.failure(e)
        }
    }

    override suspend fun deleteNote(id: Int) {
        try {
            httpClient.delete("/notes/$id")
        } catch (e: Exception) {
            Log.e("NoteRepository", "Error al eliminar nota: ${e.message}")
        }
    }
}
