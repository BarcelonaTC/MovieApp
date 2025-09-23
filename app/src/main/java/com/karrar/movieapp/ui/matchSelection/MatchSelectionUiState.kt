package com.karrar.movieapp.ui.matchSelection

import androidx.annotation.DrawableRes
import com.karrar.movieapp.R

data class MatchSelectionUiState(
    val moodList: List<Mood> = emptyList(),
    val genreList: List<GenreType> = emptyList(),
    val mediaTime: MediaTime = MediaTime.MEDIUM,
) {
    data class Mood(
        val id :Int,
        val mood: String,
        @DrawableRes val icon : Int,
        val isSelected: Boolean=false
    )

    data class GenreType(
        val id: Int,
        val name: String,
        val isSelected: Boolean = false
    )

    enum class MediaTime {
        SHORT, MEDIUM, LONG
    }
}