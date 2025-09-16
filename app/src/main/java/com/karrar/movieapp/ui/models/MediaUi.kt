package com.karrar.movieapp.ui.models

data class MediaUiState(
    val id: Int = 0,
    val imageUrl: String = "",
    val rating: String = "",
    val name: String = "",
    val typeOfMedia: TypeOfMedia = TypeOfMedia.MOVIE
)

enum class TypeOfMedia {
    MOVIE,
    TV_SHOW
}