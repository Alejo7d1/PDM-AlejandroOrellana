package com.agarcia.pdm_course_2026.movieapp.data.api.Movies

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMoviesResponseDto(
  val page: Int,
  val results: List<MovieDto>,
  @SerialName("total_pages") val totalPages: Int,
  @SerialName("total_results") val totalResults: Int
)