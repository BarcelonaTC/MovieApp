package com.karrar.movieapp.domain.mappers.actor

import com.karrar.movieapp.BuildConfig
import com.karrar.movieapp.data.remote.response.actor.ActorDto
import com.karrar.movieapp.domain.mappers.Mapper
import com.karrar.movieapp.domain.models.ActorDetails
import javax.inject.Inject

class ActorDetailsMapper @Inject constructor() : Mapper<ActorDto, ActorDetails> {
    override fun map(input: ActorDto): ActorDetails {

        val gender = if (input.gender == 1) {
            "Female"
        } else {
            "Male"
        }

        return ActorDetails(
            actorID = input.id ?: 0,
            actorName = input.name ?: "",
            actorImage = BuildConfig.IMAGE_BASE_PATH + input.profilePath,
            actorBirthday = input.birthday ?: "",
            actorPlaceOfBirth = input.placeOfBirth ?: "unknown",
            actorBiography = input.biography ?: "",
            knownForDepartment = input.knownForDepartment ?: "-",
            actorGender = gender,
            actorSocialLinks = null,
            actorProfileImages = null
        )
    }
}