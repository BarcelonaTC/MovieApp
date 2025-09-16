package com.karrar.movieapp.ui.actorDetails

import com.karrar.movieapp.ui.models.TypeOfMedia

data class ActorMoviesUIState(
    val id: Int = 0,
    val imageUrl: String = "",
    val rating: String = "",
    val name: String = "",
    val typeOfMedia: TypeOfMedia = TypeOfMedia.MOVIE
)
