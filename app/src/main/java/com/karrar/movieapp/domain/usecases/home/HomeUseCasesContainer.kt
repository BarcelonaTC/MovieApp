package com.karrar.movieapp.domain.usecase.home

import com.karrar.movieapp.domain.usecase.home.getData.movie.GetNowStreamingMoviesUseCase
import com.karrar.movieapp.domain.usecase.home.getData.movie.GetPopularMoviesUseCase
import com.karrar.movieapp.domain.usecase.home.getData.movie.GetUpcomingMoviesUseCase
import com.karrar.movieapp.domain.usecase.home.getData.series.GetTopRatedTvShowSeriesUseCase
import javax.inject.Inject

class HomeUseCasesContainer @Inject constructor(
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedTvShowUseCase: GetTopRatedTvShowSeriesUseCase,
    val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    val getNowStreamingMoviesUseCase: GetNowStreamingMoviesUseCase,
)