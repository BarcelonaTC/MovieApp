package com.karrar.movieapp.ui.adapters

import com.karrar.movieapp.R
import com.karrar.movieapp.ui.actorGallery.GalleryGroup
import com.karrar.movieapp.ui.base.BaseAdapter
import com.karrar.movieapp.ui.base.BaseInteractionListener

class GalleryAdapter(
    items: List<GalleryGroup>,
    listener: GalleryInteractionListener
) :
    BaseAdapter<GalleryGroup>(items, listener) {
    override val layoutID: Int = R.layout.item_cast_gallery

    interface GalleryInteractionListener : BaseInteractionListener {
        fun onClickBack()
    }
}