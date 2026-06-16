package com.pato.taller3.ui.options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pato.taller3.Taller3Application
import com.pato.taller3.data.model.Option
import com.pato.taller3.data.repository.OptionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OptionsViewModel(
    private val optionRepository: OptionRepository,
    private val questionId: Int
) : ViewModel() {

    val options: StateFlow<List<Option>> =
    optionRepository.getOptions(questionId)
    .stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = emptyList()
    )

    fun addOption(name: String, imageUrl: String) {
        viewModelScope.launch {
            optionRepository.addOption(name, imageUrl, questionId)
        }
    }

    fun deleteOption(option: Option) {
        viewModelScope.launch {
            optionRepository.deleteOption(option)
        }
    }

    companion object {
            fun provideFactory(questionId: Int) = viewModelFactory {
                initializer {
                    val app = this[APPLICATION_KEY] as Taller3Application
                    OptionsViewModel(app.appProvider.provideOptionRepository(), questionId)
                }
            }
        }
    }