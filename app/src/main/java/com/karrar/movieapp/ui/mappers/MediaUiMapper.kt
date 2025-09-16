package com.karrar.movieapp.ui.mappers


import com.karrar.movieapp.domain.mappers.Mapper
import com.karrar.movieapp.domain.models.Media
import com.karrar.movieapp.ui.models.MediaUiState
import com.karrar.movieapp.ui.models.TypeOfMedia
import javax.inject.Inject

class MediaUiMapper @Inject constructor() : Mapper<Media, MediaUiState> {
    override fun map(input: Media): MediaUiState {
        return MediaUiState(
            id = input.mediaID,
            imageUrl = input.mediaImage,
            name = input.mediaName,
            rating = String.format("%.1f", input.mediaRate),
            typeOfMedia = if (input.mediaType.contains("tv")) TypeOfMedia.TV_SHOW else TypeOfMedia.MOVIE
        )
    }
}