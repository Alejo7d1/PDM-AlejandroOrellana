package com.agarcia.pdm_course_2026.movieapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agarcia.pdm_course_2026.movieapp.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val overview: String,
    val releaseDate: String,
    val adult: Boolean,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val video: Boolean,
    val backdropUrl: String,
    val posterUrl: String
)

fun MovieEntity.toModel(): Movie = Movie(
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
    backdropUrl = backdropUrl,
    posterUrl = posterUrl
)