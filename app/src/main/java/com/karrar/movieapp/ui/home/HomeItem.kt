package com.karrar.movieapp.ui.home

import com.karrar.movieapp.domain.enums.HomeItemsType
import com.karrar.movieapp.ui.home.homeUiState.PopularUiState
import com.karrar.movieapp.ui.models.MediaUiState
import com.karrar.movieapp.ui.myList.myListUIState.CreatedListUIState

sealed class HomeItem(val priority: Int) {

    data class Slider(val items: List<PopularUiState>) : HomeItem(0)

    data class RecentlyReleased(
        val items: List<MediaUiState>,
        val type: HomeItemsType = HomeItemsType.RECENTLY_RELEASED
    ) : HomeItem(1)

    data object MatchCTACard : HomeItem(2)

    data class UpcomingMovies(
        val items: List<MediaUiState>,
        val type: HomeItemsType = HomeItemsType.UPCOMING_MOVIES
    ) : HomeItem(3)

    data class TopRatedTvShows(
        val items: List<MediaUiState>,
        val type: HomeItemsType = HomeItemsType.TOP_RATED_TV_SHOWS
    ) : HomeItem(4)

    data class YourCollections(
        val items: List<CreatedListUIState>,
        val type: HomeItemsType = HomeItemsType.YOUR_COLLECTIONS
    ) : HomeItem(5)

    data object ExploreCTACard : HomeItem(6)
}