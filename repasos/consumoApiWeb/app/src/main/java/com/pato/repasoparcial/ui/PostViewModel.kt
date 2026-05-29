package com.pato.repasoparcial.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pato.repasoparcial.data.model.Post
import com.pato.repasoparcial.data.remote.JsonAPI
import kotlinx.coroutines.launch

class PostViewModel(private val repository: JsonAPI) : ViewModel() { // esto hice

    var posts by mutableStateOf<List<Post>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                posts = repository.getJsonData()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Error desconocido"
            } finally {
                isLoading = false
            }
        }
    }

    fun createPost(post: Post) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val newPost = repository.postJsonData(post)
                posts = listOf(newPost) + posts
            } catch (e: Exception) {
                errorMessage = e.message ?: "Error al crear post"
            } finally {
                isLoading = false
            }
        }
    }
}
