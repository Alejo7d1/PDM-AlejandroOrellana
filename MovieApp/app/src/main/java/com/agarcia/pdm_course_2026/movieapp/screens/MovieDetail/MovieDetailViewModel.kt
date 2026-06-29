package com.agarcia.pdm_course_2026.movieapp.screens.MovieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agarcia.pdm_course_2026.movieapp.data.repositories.MovieRepository.MovieApiRepository
import com.agarcia.pdm_course_2026.movieapp.data.repositories.MovieRepository.MovieRepository
import com.agarcia.pdm_course_2026.movieapp.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
  private val movieRepository: MovieRepository = MovieApiRepository()

  private val _movie = MutableStateFlow<Movie?>(null)
  val movie = _movie.asStateFlow()

  private val _loading = MutableStateFlow<Boolean>(false)
  val loading = _loading.asStateFlow()

  fun loadMovieById(id: Int) {
    viewModelScope.launch {
      _loading.value = true
      movieRepository.getMovieById(id)
        .onSuccess { movie ->
          _movie.value = movie
        }
      _loading.value = false
    }
  }
}