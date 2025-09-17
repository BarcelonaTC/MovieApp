package com.karrar.movieapp.domain.mappers.actor

import com.karrar.movieapp.data.remote.response.actor.ActorSocialLinksDto
import com.karrar.movieapp.domain.mappers.Mapper
import javax.inject.Inject

class ActorSocialLinksMapper @Inject constructor() : Mapper<ActorSocialLinksDto, ActorSocialLinks> {
    override fun map(input: ActorSocialLinksDto): ActorSocialLinks {
        return ActorSocialLinks(
            facebook = input.facebookId?.takeIf { it.isNotBlank() }
                ?.let { "https://facebook.com/$it" },
            instagram = input.instagramId?.takeIf { it.isNotBlank() }
                ?.let { "https://instagram.com/$it" },
            twitter = input.twitterId?.takeIf { it.isNotBlank() }
                ?.let { "https://twitter.com/$it" },
            tiktok = input.tiktokId?.takeIf { it.isNotBlank() }
                ?.let { "https://www.tiktok.com/@$it" },
            youtube = input.youtubeId?.takeIf { it.isNotBlank() }
                ?.let { "https://www.youtube.com/$it" },
            imdb = input.imdbId?.takeIf { it.isNotBlank() }
                ?.let { "https://www.imdb.com/name/$it" }
        )
    }
}