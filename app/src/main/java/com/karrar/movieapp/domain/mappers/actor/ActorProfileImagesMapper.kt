package com.karrar.movieapp.domain.mappers.actor

import com.karrar.movieapp.data.remote.response.actor.ActorProfileImagesDto
import com.karrar.movieapp.domain.mappers.Mapper
import javax.inject.Inject

class ActorProfileImagesMapper @Inject constructor() :
    Mapper<ActorProfileImagesDto, ActorProfileImages> {
    override fun map(input: ActorProfileImagesDto): ActorProfileImages {
        return input.profiles.mapNotNull { it.filePath }
            .map { "https://image.tmdb.org/t/p/w500$it" }.let { ActorProfileImages(it) }
    }

}