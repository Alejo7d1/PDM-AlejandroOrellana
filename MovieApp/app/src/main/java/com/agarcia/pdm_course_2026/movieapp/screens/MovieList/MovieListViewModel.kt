package com.agarcia.pdm_course_2026.movieapp.screens.MovieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agarcia.pdm_course_2026.movieapp.data.repositories.MovieRepository.MovieApiRepository
import com.agarcia.pdm_course_2026.movieapp.data.repositories.MovieRepository.MovieRepository
import com.agarcia.pdm_course_2026.movieapp.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {
  private val movieRepository: MovieRepository = MovieApiRepository()
  private val _movies = MutableStateFlow<List<Movie>>(emptyList())
  val movies = _movies.asStateFlow()

  private val _loading = MutableStateFlow<Boolean>(false)
  val loading = _loading.asStateFlow()

  private val _refresh = MutableStateFlow<Boolean>(false)
  val refresh = _refresh.asStateFlow()

  private val _error = MutableStateFlow<String?>(null)
  val error = _error.asStateFlow()

  init {
    loadMovies()
  }

  fun loadMovies() {
    viewModelScope.launch {
      _error.value = null
      _loading.value = true

      movieRepository.getMovies()
        .onSuccess { movies ->
          _movies.value = movies

        }
        .onFailure { error ->
          _error.value =
            "Hola corazon bello, fijate que hubo error, podes hacerme el favor de presionar el boton recargar"
        }

      _loading.value = false
    }
  }

  fun refreshMovies() {
    viewModelScope.launch {
      _error.value = null
      _refresh.value = true

      movieRepository.getMovies()
        .onSuccess { movies ->
          _movies.value = movies

        }
        .onFailure { error ->
          _error.value =
            "Hola corazon bello, fijate que hubo error, podes hacerme el favor de presionar el boton recargar"
        }

      _refresh.value = false
    }
  }
}