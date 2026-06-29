package com.agarcia.pdm_course_2026.movieapp.data.repositories.MovieRepository

import com.agarcia.pdm_course_2026.movieapp.model.Movie

interface MovieRepository {
  suspend fun getMovies(): Result<List<Movie>>
  suspend fun getMovieById(id: Int): Result<Movie>
}