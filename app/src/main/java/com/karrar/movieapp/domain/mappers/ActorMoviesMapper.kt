package com.karrar.movieapp.domain.mappers

import android.annotation.SuppressLint
import com.karrar.movieapp.BuildConfig
import com.karrar.movieapp.data.remote.response.MovieDto
import com.karrar.movieapp.domain.models.ActorMovie
import javax.inject.Inject

class ActorMoviesMapper @Inject constructor() : Mapper<MovieDto, ActorMovie> {
    @SuppressLint("DefaultLocale")
    override fun map(input: MovieDto): ActorMovie {
        return ActorMovie(
            movieId = input.id ?: 0,
            movieImage = (BuildConfig.IMAGE_BASE_PATH + input.posterPath),
            movieName = input.title ?: input.originalTitle ?: "Unknown",
            movieVoteAverage = String.format("%.1f", input.voteAverage)
        )
    }
}