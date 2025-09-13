package com.karrar.movieapp.ui.actorGallery

data class GalleryGroup(
    val bigImages: List<String> =emptyList(),
    val smallImages: List<String> = emptyList(),
    val isReversed: Boolean = false
)