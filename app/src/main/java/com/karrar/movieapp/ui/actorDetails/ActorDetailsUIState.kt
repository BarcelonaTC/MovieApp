package com.karrar.movieapp.ui.actorDetails

import com.karrar.movieapp.ui.actorGallery.GalleryGroup

data class ActorDetailsUIState(
    val name: String = "",
    val imageUrl: String = "",
    val gender: String = "",
    val birthday: String = "",
    val placeOfBirth: String = "",
    val knownFor: String = "",
    val biography: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: List<Error> = emptyList(),
    val actorMovies: List<ActorMoviesUIState> = emptyList(),
    val galleryGroups: List<GalleryGroup> = emptyList(),
    val socialMedia: SocialMediaLinksUI? = null
) {
    data class SocialMediaLinksUI(
        val facebook: String? = null,
        val instagram: String? = null,
        val twitter: String? = null,
        val tiktok: String? = null,
        val youtube: String? = null,
        val imdb: String? = null
    )
}