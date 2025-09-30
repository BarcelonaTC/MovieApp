package com.karrar.movieapp.ui.adapters

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.karrar.movieapp.R
import com.karrar.movieapp.ui.base.BaseAdapter
import com.karrar.movieapp.ui.base.BaseInteractionListener

class FeaturedCollectionsAdapter(
    items: List<FeaturedCollectionUi>,
    val listener: FeaturedCollectionsInteractionListener
) :
    BaseAdapter<FeaturedCollectionUi>(items, listener) {
    override val layoutID: Int = R.layout.item_featured_collection
}

interface FeaturedCollectionsInteractionListener : BaseInteractionListener {
    fun onClickItem(featuredCollectionType: FeaturedCollectionType)
}

data class FeaturedCollectionUi(
    val type: FeaturedCollectionType,
)

enum class FeaturedCollectionType(
    @StringRes val titleRes: Int,
    @DrawableRes val imageRes: Int
) {
    LATE_NIGHT_THRILLS(
        R.string.late_night_thrills,
        R.drawable.late_night_thrills
    ),
    FAMILY_NIGHT_PICKS(
        R.string.family_night_picks,
        R.drawable.family_night_picks
    ),
    MIND_BENDING_STORIES(
        R.string.mind_bending_stories,
        R.drawable.mind_bending_stories
    ),
    BASED_ON_TRUE_EVENTS(
        R.string.based_on_true_events,
        R.drawable.based_on_true_events
    ),
    CINEMATIC_MASTERPIECE(
        R.string.cinematic_masterpieces,
        R.drawable.cinematic_masterpieces
    ),
    FEEL_GOOD_PREFERENCES(
        R.string.feel_good_favorites,
        R.drawable.feel_good_favorites
    )
}