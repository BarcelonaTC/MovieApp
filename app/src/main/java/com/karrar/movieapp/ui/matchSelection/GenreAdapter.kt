package com.karrar.movieapp.ui.matchSelection

import com.karrar.movieapp.R
import com.karrar.movieapp.ui.base.BaseAdapter
import com.karrar.movieapp.ui.base.BaseInteractionListener

class GenreAdapter (items: List<MatchSelectionUiState.GenreType>, val listener: GenreInteractionListener) :
    BaseAdapter<MatchSelectionUiState.GenreType>(items, listener) {
    override val layoutID: Int = R.layout.item_genre
}

interface GenreInteractionListener : BaseInteractionListener {
    fun onClickGenre(actorID: Int)
}