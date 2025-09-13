package com.karrar.movieapp.domain.models

import com.karrar.movieapp.domain.mappers.actor.ActorProfileImages
import com.karrar.movieapp.domain.mappers.actor.ActorSocialLinks

data class ActorDetails(
    val actorID: Int,
    val actorName: String,
    val actorImage: String,
    val actorBirthday: String,
    val actorPlaceOfBirth: String,
    val actorBiography: String,
    val knownForDepartment: String,
    val actorGender: String,
    val actorSocialLinks: ActorSocialLinks? = null,
    val actorProfileImages: ActorProfileImages?=null
)