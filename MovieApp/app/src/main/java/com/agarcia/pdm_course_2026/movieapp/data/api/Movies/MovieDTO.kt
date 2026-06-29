package com.agarcia.pdm_course_2026.movieapp.data.api.Movies

import com.agarcia.pdm_course_2026.movieapp.data.database.entities.MovieEntity
import com.agarcia.pdm_course_2026.movieapp.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
  val id: Int,
  val title: String,
  @SerialName("original_title") val originalTitle: String,
  @SerialName("original_language") val originalLanguage: String,
  val overview: String,
  @SerialName("release_date") val releaseDate: String,
  val adult: Boolean,
  val popularity: Double,
  @SerialName("vote_average") val voteAverage: Double,
  @SerialName("vote_count") val voteCount: Int,
  val video: Boolean,
  @SerialName("backdrop_path") val backdropPath: String?,
  @SerialName("poster_path") val posterPath: String?
)

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieDto.toEntity(): MovieEntity = MovieEntity(
  id = id,
  title = title,
  originalTitle = originalTitle,
  originalLanguage = originalLanguage,
  overview = overview,
  releaseDate = releaseDate,
  adult = adult,
  popularity = popularity,
  voteAverage = voteAverage,
  voteCount = voteCount,
  video = video,
  backdropUrl = backdropPath?.let { "$IMAGE_BASE_URL$it" } ?: "",
  posterUrl = posterPath?.let { "$IMAGE_BASE_URL$it" } ?: ""
)

fun MovieDto.toModel(): Movie {
  return Movie(
    id = id,
    title = title,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    overview = overview,
    releaseDate = releaseDate,
    adult = adult,
    genreIds = emptyList(),
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
    video = video,
    backdropUrl = backdropPath?.let { "$IMAGE_BASE_URL$it" } ?: "",
    posterUrl = posterPath?.let { "$IMAGE_BASE_URL$it" } ?: ""
  )
}

