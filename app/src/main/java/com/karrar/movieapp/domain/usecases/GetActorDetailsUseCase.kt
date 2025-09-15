package com.karrar.movieapp.domain.usecases

import com.karrar.movieapp.data.repository.MovieRepository
import com.karrar.movieapp.domain.mappers.MovieMappersContainer
import com.karrar.movieapp.domain.models.ActorDetails
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieMappersContainer: MovieMappersContainer
) {
    suspend operator fun invoke(actorId: Int): ActorDetails {
        val actorDto = movieRepository.getActorDetails(actorId)
            ?: throw IllegalStateException("Actor details not found for id=$actorId")

        val profileImagesDto = movieRepository.getActorProfileImages(actorId)
        val socialLinksDto = movieRepository.getActorSocialLinks(actorId)

        val actorDetails = movieMappersContainer.actorDetailsMapper.map(actorDto)

        return actorDetails.copy(
            actorProfileImages = profileImagesDto?.let {
                movieMappersContainer.actorProfileImagesMapper.map(
                    it
                )
            },
            actorSocialLinks = socialLinksDto?.let {
                movieMappersContainer.actorSocialLinksMapper.map(
                    it
                )
            }
        )
    }
}
