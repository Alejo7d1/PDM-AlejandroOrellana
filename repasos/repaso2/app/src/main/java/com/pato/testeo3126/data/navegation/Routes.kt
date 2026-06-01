package com.pato.testeo3126.data.navegation
import kotlinx.serialization.Serializable

import androidx.navigation3.runtime.NavKey

sealed class Routes : NavKey {
    @Serializable
    data object Home : Routes()

    @Serializable
    data object Notes : Routes()

    @Serializable
    data object Add : Routes()

    @Serializable
    data class NoteDetail(val id: Int): Routes()
}