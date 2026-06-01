package com.pato.testeo3126.data.remote

import com.pato.testeo3126.data.dto.NoteDTO
import com.pato.testeo3126.data.model.Note

interface NoteAPI {
    suspend fun getNotes(): List<Note>

    suspend fun getNotebyId(id: Int): Note

    suspend fun postNote(title: String, body: String, author: String): Result<NoteDTO>

    suspend fun deleteNote(id: Int)

}