package com.karrar.movieapp.ui.myList.listDetails

import com.karrar.movieapp.domain.mappers.Mapper
import com.karrar.movieapp.domain.models.SaveListDetails
import com.karrar.movieapp.ui.myList.listDetails.listDetailsUIState.SavedMediaUIState
import com.karrar.movieapp.utilities.DataFormatter
import com.karrar.movieapp.utilities.roundToOneDecimal
import javax.inject.Inject

class MediaUIStateMapper @Inject constructor() : Mapper<SaveListDetails, SavedMediaUIState> {

    override fun map(input: SaveListDetails): SavedMediaUIState {
        return SavedMediaUIState(
            image = input.posterPath,
            mediaID = input.id,
            title = input.title,
            movieGenre = input.genres,
            runtime = DataFormatter().runtimeToDate(input.runtime),
            voteAverage = input.voteAverage.roundToOneDecimal(),
            releaseDate = DataFormatter().releasedDate(input.releaseDate),
            mediaType = input.mediaType
        )
    }
}