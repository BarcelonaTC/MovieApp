package com.karrar.movieapp.domain.usecases

import com.karrar.movieapp.data.repository.MovieRepository
import com.karrar.movieapp.domain.mappers.MovieMappersContainer
import com.karrar.movieapp.domain.mappers.actor.ActorProfileImages
import com.karrar.movieapp.ui.actorGallery.GalleryGroup
import javax.inject.Inject

class GetActorImagesUseCase  @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieMappersContainer: MovieMappersContainer,
) {
   suspend operator  fun invoke(actorId: Int): ActorProfileImages{
       val actorProfileImages = movieRepository.getActorProfileImages(actorId)
           ?: throw IllegalStateException("Actor profile images not found for id=$actorId")
       return movieMappersContainer.actorProfileImagesMapper.map(actorProfileImages)
    }
}