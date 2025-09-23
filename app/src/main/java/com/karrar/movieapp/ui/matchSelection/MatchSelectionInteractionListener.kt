package com.karrar.movieapp.ui.matchSelection

import com.karrar.movieapp.ui.base.BaseInteractionListener

interface MatchSelectionInteractionListener {
    fun onMoodClicked(mood: MatchSelectionUiState.Mood)
}