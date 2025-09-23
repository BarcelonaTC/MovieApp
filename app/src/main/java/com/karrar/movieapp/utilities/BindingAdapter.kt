package com.karrar.movieapp.utilities

import android.graphics.Rect
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.ChipGroup
import com.karrar.movieapp.R
import com.karrar.movieapp.domain.enums.MediaType
import com.karrar.movieapp.ui.base.BaseAdapter
import com.karrar.movieapp.ui.category.uiState.ErrorUIState
import com.karrar.movieapp.ui.category.uiState.GenreUIState
import com.karrar.movieapp.utilities.Constants.FIRST_CATEGORY_ID
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs


@BindingAdapter("app:showWhenListNotEmpty")
fun <T> showWhenListNotEmpty(view: View, list: List<T>) {
    view.isVisible = list.isNotEmpty() == true
}

@BindingAdapter("app:showWhenListEmpty")
fun <T> showWhenListEmpty(view: View, list: List<T>) {
    view.isVisible = list.isEmpty() == true
}

@BindingAdapter(value = ["toggleViewsOnScroll", "targetViewId"], requireAll = true)
fun ScrollView.toggleViewsOnScroll(viewIds: IntArray?, targetViewId: Int) {
    if (viewIds == null) return
    post {
        val targetView = rootView.findViewById<View>(targetViewId)
        if (targetView == null) return@post
        viewTreeObserver.addOnScrollChangedListener {
            val rect = Rect()
            val isVisible = targetView.getGlobalVisibleRect(rect)

            viewIds.forEach { id ->
                val v = rootView.findViewById<View>(id)
                v?.visibility = if (isVisible) View.GONE else View.VISIBLE
            }
        }
    }
}

@BindingAdapter("app:hideWhenListIsEmpty")
fun <T> hideWhenListIsEmpty(view: View, list: List<T>?) {
    if (list?.isEmpty() == true) {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter(value = ["app:error", "app:loading"])
fun <T> showWhenSuccess(view: View, error: List<T>?, loading: Boolean) {
    view.isVisible = error?.isEmpty() == true && !loading
}

@BindingAdapter(value = ["app:noError", "app:doneLoad", "app:emptyData"])
fun <T, M> showWhenNoData(view: View, error: List<T>?, loading: Boolean, data: List<M>?) {
    view.isVisible = error.isNullOrEmpty() && !loading && data.isNullOrEmpty()
}

@BindingAdapter(value = ["app:errorNotEmpty", "app:doneLoading"])
fun <T> hidWhenFail(view: View, error: List<T>?, loading: Boolean) {
    view.visibility = if (!error.isNullOrEmpty() && !loading) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("app:isListEmpty")
fun showWhenDoneLoadingAndListIsEmpty(view: View, emptyList: Boolean) {
    view.isVisible = emptyList
}

@BindingAdapter(value = ["app:showWhenNoInternet"])
fun showWhenNoInternet(view: View, error: List<ErrorUIState>) {
    view.isVisible = !error.none { it.code != ErrorUI.NEED_LOGIN }
}

@BindingAdapter(value = ["app:showWhenNoLogin"])
fun showWhenNoLogin2(view: View, error: List<ErrorUIState>) {
    view.isVisible = !error.none { it.code == ErrorUI.NEED_LOGIN }
}

@BindingAdapter("app:showWhenNoLoggedIn")
fun showWhenNoLoggedIn(view: View, isLoggedIn: Boolean) {
    view.isVisible = !isLoggedIn
}

@BindingAdapter("app:isVisible")
fun isVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("app:hideIfTrue")
fun hideIfTrue(view: View, value: Boolean) {
    view.isVisible = !value
}

@BindingAdapter("app:isLoggedIn", "app:isFail")
fun showWhenLoggedInAndFail(view: View, isLoggedIn: Boolean, isFail: Boolean) {
    if (isLoggedIn && isFail) {
        view.isVisible = true
    } else if (isLoggedIn) {
        view.isVisible = false
    }
}

@BindingAdapter("isLogged", "isFailure")
fun showWhenIsLoggedInWithoutFail(view: View, isLoggedIn: Boolean, isFail: Boolean) {
    if (isLoggedIn && !isFail) {
        view.isVisible = true
    } else if (isFail) {
        view.isVisible = false
    }
}

//Search
@BindingAdapter(value = ["app:showWhenSearch"])
fun showWhenSearch(view: View, text: String) {
    view.isVisible = text.isNotBlank()
}

@BindingAdapter(value = ["app:hideWhenSearch"])
fun hideWhenSearch(view: View, text: String) {
    view.isVisible = text.isBlank()
}

@BindingAdapter(value = ["app:hideWhenBlankSearch"])
fun hideWhenBlankSearch(view: View, text: String) {
    if (text.isBlank()) {
        view.visibility = View.INVISIBLE
    }
}


@BindingAdapter(value = ["app:searchInput", "app:errorSearch", "app:loadingSearch"])
fun <T> hideWhenSuccessSearch(view: View, text: String, error: List<T>?, loading: Boolean) {
    view.visibility = if (text.isNotBlank() && error.isNullOrEmpty() && !loading) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

// different

@BindingAdapter(value = ["app:items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItems(items ?: emptyList())
    view.scrollToPosition(0)
}

@BindingAdapter("app:reverseGalleryLayout")
fun reverseGalleryLayout(view: ConstraintLayout, reverse: Boolean) {
    view.layoutDirection = if (reverse) {
        View.LAYOUT_DIRECTION_RTL
    } else {
        View.LAYOUT_DIRECTION_LTR
    }
}

@BindingAdapter("app:chipBackground")
fun View.setChipBackground(isSelected: Boolean) {
    val bgRes = if (isSelected) R.drawable.shape_chip_enabled_radius12
    else R.drawable.shape_chip_disabled_radius12
    setBackgroundResource(bgRes)
}

@BindingAdapter("app:chipIconBackground")
fun View.setChipIconBackground(isSelected: Boolean) {
    val bgRes = if (isSelected) R.drawable.shape_chip_icon_enabled_radius10
    else R.drawable.shape_chip_icon_disabled_radius10
    setBackgroundResource(bgRes)
}

@BindingAdapter(value = ["app:usePagerSnapHelper"])
fun usePagerSnapHelperWithRecycler(recycler: RecyclerView, useSnapHelper: Boolean = false) {
    if (useSnapHelper)
        PagerSnapHelper().attachToRecyclerView(recycler)
}

@BindingAdapter("app:posterImage")
fun bindMovieImage(image: ImageView, imageURL: String?) {
    imageURL?.let {
        image.load(imageURL) {
            placeholder(R.drawable.loading)
            error(R.drawable.ic_profile_place_holder)
        }
    }
}

@BindingAdapter(value = ["actorGalleryImage", "placeholderImage"], requireAll = true)
fun bindActorGalleryImage(
    image: ImageView,
    imageURL: String?,
    placeholder: ImageView
) {
    if (!imageURL.isNullOrEmpty()) {
        placeholder.visibility = View.GONE

        image.load(imageURL) {
            crossfade(true)
            placeholder(R.drawable.loading)
            fallback(R.drawable.actor_placeholder)
            listener(
                onStart = {
                    placeholder.visibility = View.GONE
                },
                onSuccess = { _, _ ->
                    placeholder.visibility = View.GONE
                },
                onError = { _, _ ->
                    placeholder.visibility = View.VISIBLE
                }
            )
        }
    }
}

@BindingAdapter("app:mediaPoster")
fun loadMediaPoster(image: ImageView, imageURL: String?) {
    imageURL?.let {
        image.load(imageURL) {
            placeholder(R.drawable.loading)
            error(R.drawable.media_place_holder)
        }
    }
}

@BindingAdapter("app:showProfileWhenSuccess")
fun showWhenProfileSuccess(view: View, userName: String) {
    view.isVisible = userName.isNotEmpty()
}

@BindingAdapter("app:overviewText")
fun setOverViewText(view: TextView, text: String) {
    if (text.isNotEmpty()) {
        view.text = text
    } else {
        view.text = view.context.getString(R.string.empty_overview_text)
    }
}

@BindingAdapter("app:setVideoId")
fun setVideoId(view: YouTubePlayerView, videoId: String?) {
    view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            videoId?.let { youTubePlayer.cueVideo(it, 0f) }
        }
    })
}

@BindingAdapter("app:setReleaseDate")
fun setReleaseDate(text: TextView, date: String?) {
    text.text = date?.take(4)
}

@BindingAdapter("app:convertToHoursPattern")
fun convertToHoursPattern(view: TextView, duration: Int) {
    duration.let {
        val hours = (duration / 60).toString()
        val minutes = (duration % 60).toString()
        if (hours == "0") {
            view.text = view.context.getString(R.string.minutes_pattern, minutes)
        } else if (minutes == "0") {
            view.text = view.context.getString(R.string.hours_pattern, hours)
        } else {
            view.text = view.context.getString(R.string.hours_minutes_pattern, hours, minutes)
        }
    }
}

@BindingAdapter(value = ["app:movieHours", "app:movieMinutes"])
fun setDuration(view: TextView, hours: Int?, minutes: Int?) {
    if (hours == 0) {
        view.text = String.format(view.context.getString(R.string.minutes_pattern), minutes)
    } else if (minutes == 0) {
        view.text = String.format(view.context.getString(R.string.hours_pattern), hours)
    } else {
        view.text =
            String.format(view.context.getString(R.string.hours_minutes_pattern), hours, minutes)
    }
}

@BindingAdapter("app:formatDate")
fun formatDate(textView: TextView, dateString: String?) {
    if (!dateString.isNullOrEmpty()) {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(dateString)

            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val formattedDate = outputFormat.format(date)

            textView.text = textView.context.getString(R.string.born_on, formattedDate)
        } catch (e: Exception) {
            textView.text = textView.context.getString(R.string.born_on, dateString)
        }
    } else {
        textView.text = ""
    }
}

@BindingAdapter("app:setGenres", "app:listener", "app:selectedChip")
fun <T> setGenresChips(
    view: ChipGroup, chipList: List<GenreUIState>?, listener: T,
    selectedChip: Int?
) {
    chipList?.let {
        it.forEach { genre -> view.addView(view.createChip(genre, listener)) }
    }
    val index = chipList?.indexOf(chipList.find { it.genreID == selectedChip }) ?: FIRST_CATEGORY_ID
    view.getChildAt(index)?.id?.let { view.check(it) }
}

@BindingAdapter("app:genre")
fun setAllGenre(textView: TextView, genreList: List<String>?) {
    genreList?.let {
        textView.text = genreList.joinToString(" , ") { it }
    }
}

@BindingAdapter("app:hideIfNotTypeOfMovie")
fun hideIfNotTypeOfMovie(view: View, mediaType: MediaType?) {
    if (mediaType != MediaType.MOVIE) view.isVisible = false
}

@BindingAdapter("android:rating")
fun setRating(view: RatingBar?, rating: Float) {
    view?.let {
        view.rating = rating
    }
}

@BindingAdapter("showWhenTextNotEmpty")
fun <T> showWhenTextNotEmpty(view: View, text: String) {
    view.isVisible = text.isNotEmpty()
}

@BindingAdapter("viewPagerAdapter")
fun setViewPagerAdapter(viewPager: ViewPager2, adapter: RecyclerView.Adapter<*>?) {
    adapter?.let {
        viewPager.adapter = it
    }
}

@BindingAdapter("setupCarousel")
fun setupCarousel(viewPager: ViewPager2, adapter: BaseAdapter<*>) {

    viewPager.adapter = adapter
    viewPager.offscreenPageLimit = 3
    viewPager.clipToPadding = false
    viewPager.clipChildren = false
    viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    val nextItemVisiblePx = 48
    viewPager.setPadding(nextItemVisiblePx, 0, nextItemVisiblePx, 0)

    val compositePageTransformer = CompositePageTransformer().apply {
        addTransformer { page, position ->

            val pageWidth = page.width
            page.translationX = -position * (pageWidth * 0.18f)
            val isCenter = abs(position) < 0.1f

            if (isCenter) {
                page.scaleY = 1f
                page.alpha = 1f
                page.translationY = 0f
                page.translationZ = 1f

            } else {
                page.scaleY = 1.15f
                page.alpha = 0.6f
                page.translationY = 40f
                page.translationZ = 0f
            }


            val title = page.findViewById<TextView>(R.id.title_popular_text_view)
            val genre = page.findViewById<TextView>(R.id.genre_popular_text_view)
            val rating = page.findViewById<MaterialCardView>(R.id.rating_popular_movie)

            if (isCenter) {
                title?.visibility = View.VISIBLE
                genre?.visibility = View.VISIBLE
                rating?.visibility = View.VISIBLE
            } else {
                title?.visibility = View.GONE
                genre?.visibility = View.GONE
                rating?.visibility = View.GONE
            }
        }
    }

    viewPager.setPageTransformer(compositePageTransformer)

    val handler = android.os.Handler(Looper.getMainLooper())
    val runnable = object : Runnable {
        override fun run() {
            val itemCount = adapter.itemCount
            if (itemCount > 0) {
                val nextItem = (viewPager.currentItem + 1) % itemCount
                viewPager.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 4000)
            }
        }
    }

    handler.postDelayed(runnable, 4000)

    viewPager.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {}
        override fun onViewDetachedFromWindow(v: View) {
            handler.removeCallbacks(runnable)
        }
    })
}