package com.karrar.movieapp.ui.matchSelection

import com.karrar.movieapp.R
import com.karrar.movieapp.ui.base.BaseViewModel
import com.karrar.movieapp.ui.matchSelection.MatchSelectionUiState.Mood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MatchSelectionViewModel @Inject constructor() : BaseViewModel(),
    MatchSelectionInteractionListener, GenreInteractionListener {
    private val _matchSelectionUIState = MutableStateFlow(
        MatchSelectionUiState(
            moodList = listOf(
                Mood(0, "Chill", R.drawable.headphone, isSelected = true),
                Mood(1, "Excited", R.drawable.flame),
                Mood(2, "Emotional", R.drawable.heart, isSelected = true),
                Mood(3, "Curious", R.drawable.search)
            ),
            genreList = listOf(
                MatchSelectionUiState.GenreType(0, "Action", isSelected = false),
                MatchSelectionUiState.GenreType(1, "Comedy", isSelected = true),
                MatchSelectionUiState.GenreType(2, "Drama", isSelected = false),
                MatchSelectionUiState.GenreType(3, "Romance", isSelected = true),
                MatchSelectionUiState.GenreType(4, "Sci-Fi", isSelected = false),
                MatchSelectionUiState.GenreType(5, "Thriller", isSelected = true),
                MatchSelectionUiState.GenreType(6, "Animation", isSelected = false),
                MatchSelectionUiState.GenreType(7, "Mystery", isSelected = false),
            )
        )
    )
    val matchSelectionUIState = _matchSelectionUIState.asStateFlow()
    override fun getData() {
        TODO("Not yet implemented")
    }

    override fun onMoodClicked(mood: Mood) {
        val updated = _matchSelectionUIState.value.moodList.map {
            if (it.id == mood.id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        _matchSelectionUIState.value =
            _matchSelectionUIState.value.copy(moodList = updated)
    }

    override fun onClickGenre(actorID: Int) {
        TODO("Not yet implemented")
    }
}