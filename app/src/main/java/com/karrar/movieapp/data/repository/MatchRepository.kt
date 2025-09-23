package com.karrar.movieapp.data.repository

import com.karrar.movieapp.domain.models.MovieDetails
import com.karrar.movieapp.domain.models.TvShowDetails

interface MatchRepository {
    suspend fun getMatchingMovies(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestFirstAirDate: String?,
        latestFirstAirDate: String?,
        moodId: String? = null,
    ): List<MovieDetails>

    suspend fun getMatchingSeries(
        genreIds: String,
        minRuntime: Int?,
        maxRuntime: Int?,
        earliestFirstAirDate: String?,
        latestFirstAirDate: String?,
        moodId: String? = null,
    ): List<TvShowDetails>
}