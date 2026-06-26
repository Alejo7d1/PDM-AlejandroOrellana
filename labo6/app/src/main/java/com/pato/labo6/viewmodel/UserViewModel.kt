package com.pato.labo6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pato.labo6.data.model.User
import com.pato.labo6.data.repository.UserRepository
import com.pato.labo6.data.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class UserViewModel(
    private val repository: UserRepository = UserRepositoryImpl()
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()


    suspend fun loadUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getUsers()
                .onSuccess { _users.value = it }
                .onFailure { it.printStackTrace() }
            _isLoading.value = false
        }

        repository.getUsers()
            .onSuccess {
                _users.value = it
                _errorMessage.value = null
            }
            .onFailure {
                _errorMessage.value = "No se pudieron cargar los datos"
            }

    }


}
