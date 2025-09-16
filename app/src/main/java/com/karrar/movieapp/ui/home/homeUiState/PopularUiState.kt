package com.karrar.movieapp.ui.home.homeUiState

import com.karrar.movieapp.ui.models.TypeOfMedia

data class PopularUiState(
    val movieID: Int,
    val title: String,
    val imageUrl: String,
    val movieRate:Double,
    val genre: List<String>,
    val typeOfMedia: TypeOfMedia = TypeOfMedia.MOVIE
)