package com.karrar.movieapp.ui.home.homeUiState

import com.karrar.movieapp.ui.home.HomeItem

data class HomeUiState(
    val userName: String = "",
    val popularMovies: HomeItem = HomeItem.Slider(emptyList()),
    val recentlyReleased: HomeItem = HomeItem.RecentlyReleased(emptyList()),
    val upcomingMovies: HomeItem = HomeItem.UpcomingMovies(emptyList()),
    val topRatedTvShows: HomeItem = HomeItem.TopRatedTvShows(emptyList()),
    val matchCRACard: HomeItem = HomeItem.MatchCTACard,
    val exploreCRACard: HomeItem = HomeItem.ExploreCTACard,
    val yourCollections: HomeItem = HomeItem.YourCollections(emptyList()),
    val isLoading: Boolean = false,
    val error: List<String> = emptyList(),
)