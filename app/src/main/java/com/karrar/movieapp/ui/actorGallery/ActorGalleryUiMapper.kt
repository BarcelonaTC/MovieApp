package com.karrar.movieapp.ui.actorGallery

import com.karrar.movieapp.domain.mappers.Mapper
import com.karrar.movieapp.domain.mappers.actor.ActorProfileImages
import com.karrar.movieapp.utilities.splitImagesIntoGroups
import javax.inject.Inject

class ActorGalleryUiMapper @Inject constructor() : Mapper<ActorProfileImages, ActorGalleryUiState> {
    override fun map(input: ActorProfileImages): ActorGalleryUiState {
        return ActorGalleryUiState(
            galleryGroups = splitImagesIntoGroups(input.images)
        )
    }
}