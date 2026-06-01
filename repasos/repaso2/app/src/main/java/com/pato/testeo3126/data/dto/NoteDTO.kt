package com.pato.testeo3126.data.dto

import com.pato.testeo3126.data.model.Note
import kotlinx.serialization.Serializable

@Serializable
data class NoteDTO (
    val id: Int,
    val title: String,
    val body: String,
    val author: String,
    val date: String
)

fun NoteDTO.toModel(): Note{
    return Note(
        id = id,
        title = title,
        body = body,
        author = author,
        date = date
    )
}