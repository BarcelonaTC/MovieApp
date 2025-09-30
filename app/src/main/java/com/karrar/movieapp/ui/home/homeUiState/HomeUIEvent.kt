package com.karrar.movieapp.ui.home.homeUiState

import com.karrar.movieapp.domain.enums.AllMediaType
import com.karrar.movieapp.ui.myList.myListUIState.CreatedListUIState

sealed interface HomeUIEvent {
    data class ClickMovieEvent(val movieID: Int) : HomeUIEvent
    data class ClickSeriesEvent(val seriesID: Int) : HomeUIEvent
    data class ClickSeeAllMovieEvent(val mediaType: AllMediaType) : HomeUIEvent
    data class ClickSeeAllTVShowsEvent(val mediaType: AllMediaType) : HomeUIEvent

    data class ClickCollection(val createdListUIState: CreatedListUIState) : HomeUIEvent

    data object ClickSeeAllYourCollection : HomeUIEvent

    data class ClickFeaturedCollectionEvent(val mediaType: AllMediaType) : HomeUIEvent

}