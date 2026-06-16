package com.pato.taller3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.pato.taller3.ui.options.OptionsScreen
import com.pato.taller3.ui.questions.QuestionsScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object Questions : Route()

    @Serializable
    data class Options(val questionId: Int) : Route()
}

class AppNavigator(private val backStack: MutableList<Route>) {
    fun navigateToOptions(questionId: Int) {
        backStack.add(Route.Options(questionId))
    }

    fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.size - 1)
        }
    }
}

@Composable
fun AppNavigation() {
    val backStack = remember { mutableStateListOf<Route>(Route.Questions) }
    val navigator = remember(backStack) { AppNavigator(backStack) }

    NavDisplay(
        backStack = backStack,
        onBack = { navigator.navigateBack() },
    ) { route ->
        when (route) {
            is Route.Questions -> NavEntry(route) {
                QuestionsScreen(
                    onQuestionClick = { id ->
                        navigator.navigateToOptions(id)
                    }
                )
            }
            is Route.Options -> NavEntry(route) {
                OptionsScreen(
                    questionId = route.questionId,
                    onBack = { navigator.navigateBack() }
                )
            }
        }
    }
}
