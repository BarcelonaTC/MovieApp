package com.karrar.movieapp.ui.actorGallery

import com.karrar.movieapp.ui.actorDetails.ActorDetailsUIEvent

sealed interface ActorGalleryUIEvent {
    object BackEvent : ActorGalleryUIEvent
}