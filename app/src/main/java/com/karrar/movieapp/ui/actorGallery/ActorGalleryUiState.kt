package com.karrar.movieapp.ui.actorGallery

import com.karrar.movieapp.ui.actorDetails.Error

data class ActorGalleryUiState(
    val name: String = "",
    val galleryGroups: List<GalleryGroup> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: List<Error> = emptyList(),
)