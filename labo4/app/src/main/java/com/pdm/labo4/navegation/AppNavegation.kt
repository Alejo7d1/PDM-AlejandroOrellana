package com.pdm.labo4.navegation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.pdm.labo4.screen.HomeScreen
import com.pdm.labo4.screen.TaskScreen
import com.pdm.labo4.viewmodel.GeneralViewModel

@Composable
fun AppNavegation() {
    val backStack = remember { mutableStateListOf<Any>(Home) }
    val viewModel: GeneralViewModel = viewModel()

    NavDisplay(
        backStack = backStack,
        onBack = { if (backStack.size > 1) backStack.removeAt(backStack.size - 1) },
        entryProvider = { key ->
            when (key) {
                is Home -> NavEntry(key) {
                    HomeScreen(onNavigateToTasks = { backStack.add(Tasks) })
                }
                is Tasks -> NavEntry(key) {
                    TaskScreen(viewModel = viewModel)
                }
                else -> NavEntry(Unit) { Text("Pantalla no encontrada") }
            }
        }
    )
}
