package com.karrar.movieapp.ui.adapters

import com.karrar.movieapp.R
import com.karrar.movieapp.domain.enums.HomeItemsType
import com.karrar.movieapp.ui.base.BaseAdapter
import com.karrar.movieapp.ui.base.BaseInteractionListener
import com.karrar.movieapp.ui.models.MediaUiState

class SeriesAdapter(items: List<MediaUiState>, val listener: SeriesInteractionListener) :
    BaseAdapter<MediaUiState>(items, listener) {
    override val layoutID: Int = R.layout.item_movie
}

interface SeriesInteractionListener : BaseInteractionListener {
    fun onClickSeries(seriesId: Int)
    fun onClickSeeAllSeries(homeItemsType: HomeItemsType)
}
