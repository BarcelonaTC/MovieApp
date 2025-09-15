package com.karrar.movieapp.ui.actorDetails

import com.karrar.movieapp.domain.mappers.Mapper
import com.karrar.movieapp.domain.mappers.actor.ActorSocialLinks
import com.karrar.movieapp.domain.models.ActorDetails
import com.karrar.movieapp.utilities.splitImagesIntoGroups
import javax.inject.Inject

class ActorDetailsUIMapper @Inject constructor() : Mapper<ActorDetails, ActorDetailsUIState> {
    override fun map(input: ActorDetails): ActorDetailsUIState {
        return ActorDetailsUIState(
            name = input.actorName,
            imageUrl = input.actorImage,
            gender = input.actorGender,
            birthday = input.actorBirthday,
            biography = input.actorBiography,
            placeOfBirth = input.actorPlaceOfBirth,
            knownFor = input.knownForDepartment,
            galleryGroups = splitImagesIntoGroups(input.actorProfileImages?.images.orEmpty()),
            socialMedia = input.actorSocialLinks?.toUI()
        )
    }

    private fun ActorSocialLinks.toUI() = ActorDetailsUIState.SocialMediaLinksUI(
        facebook = facebook,
        instagram = instagram,
        twitter = twitter,
        tiktok = tiktok,
        youtube = youtube,
        imdb = imdb
    )
}