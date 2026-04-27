package com.pdm0126.repasoparcial

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

//Ventana Principal, data object es para estados
@Serializable
data object Home
//Ventana de detalles, data class son las clases de toda la vida
@Serializable
data class Detail(val id: String)

//Clase de navigation controla la navegacion por pilas
@Composable
fun BasicNavigationWrapper(){
    val backStack = remember { mutableStateListOf<Any>(Home) }

    NavDisplay(
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider = { key ->
            when(key){
                is Home -> NavEntry(key){
                    ViewContainer{ id ->
                        backStack.add(Detail(id))
                    }
                }
                is Detail -> NavEntry(key){
                    DetailScreen(key.id)
                }
                else -> NavEntry(key = Unit){
                    Text("Error :(")
                }
            }
        }
    )
}