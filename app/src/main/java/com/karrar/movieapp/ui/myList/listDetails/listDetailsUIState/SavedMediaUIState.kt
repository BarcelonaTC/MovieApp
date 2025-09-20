package com.karrar.movieapp.ui.myList.listDetails.listDetailsUIState

import com.karrar.movieapp.utilities.Constants

data class SavedMediaUIState(
    val mediaID: Int = 0,
    val title: String = "",
    val movieGenre: String = "",
    val runtime: String =" 0",
    val mediaType: String = Constants.MOVIE,
    val voteAverage: Double = 0.0,
    val releaseDate: String = "",
    val image: String = "",
)