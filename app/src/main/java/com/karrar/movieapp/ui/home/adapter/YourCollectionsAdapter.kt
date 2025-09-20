package com.karrar.movieapp.ui.home.adapter

import com.karrar.movieapp.R
import com.karrar.movieapp.ui.base.BaseAdapter
import com.karrar.movieapp.ui.base.BaseInteractionListener
import com.karrar.movieapp.ui.myList.myListUIState.CreatedListUIState

class YourCollectionsAdapter(
    items: List<CreatedListUIState>,
    val listener: YourCollectionsInteractionListener
) : BaseAdapter<CreatedListUIState>(items, listener) {
    override val layoutID: Int = R.layout.item_collection
}

interface YourCollectionsInteractionListener : BaseInteractionListener {
    fun onListClick(item: CreatedListUIState)
    fun onClickSeeAllYourCollections()
}