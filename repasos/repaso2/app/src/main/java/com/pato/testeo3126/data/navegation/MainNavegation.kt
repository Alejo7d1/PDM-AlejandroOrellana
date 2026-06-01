package com.pato.testeo3126.data.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.pato.testeo3126.data.screens.AddScreen
import com.pato.testeo3126.data.screens.HomeScreen
import com.pato.testeo3126.data.screens.NoteDetailScreen
import com.pato.testeo3126.data.screens.NotesScreen

@Composable
fun MainNavigation() {
    val backStack = remember { mutableStateListOf<Routes>(Routes.Home) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when(key) {
                is Routes.Home -> NavEntry(key) {
                    HomeScreen(
                        onNavigateToNotes = { backStack.add(Routes.Notes) },
                        onNavigateToAdd = { backStack.add(Routes.Add) }
                    )
                }
                is Routes.Add -> NavEntry(key) {
                    AddScreen(onBack = { backStack.removeLastOrNull() })
                }
                is Routes.Notes -> NavEntry(key) {
                    NotesScreen(
                        onNoteClick = { id -> backStack.add(Routes.NoteDetail(id)) }
                    )
                }
                is Routes.NoteDetail -> NavEntry(key) {
                    NoteDetailScreen(id = key.id, onBack = { backStack.removeLastOrNull() })
                }
            }
        }
    )
}
