package com.karrar.movieapp.ui.allMedia

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentAllMovieBinding
import com.karrar.movieapp.domain.enums.AllMediaType
import com.karrar.movieapp.ui.adapters.LoadUIStateAdapter
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.ui.models.MediaUiState
import com.karrar.movieapp.utilities.collect
import com.karrar.movieapp.utilities.collectLast
import com.karrar.movieapp.utilities.setSpanSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMovieFragment : BaseFragment<FragmentAllMovieBinding>() {
    override val layoutIdFragment = R.layout.fragment_all_movie
    override val viewModel: AllMovieViewModel by viewModels()
    private val allMediaAdapter: AllMediaAdapter by lazy { AllMediaAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true, getTitle(viewModel.args.type))
        setMovieAdapter()
        collectEvent()
    }

    private fun setMovieAdapter() {
        val footerAdapter = LoadUIStateAdapter(allMediaAdapter::retry)
        binding.recyclerMedia.adapter = allMediaAdapter.withLoadStateFooter(footerAdapter)

        val mManager = binding.recyclerMedia.layoutManager as GridLayoutManager
        mManager.setSpanSize(footerAdapter, allMediaAdapter, mManager.spanCount)

        collect(
            flow = allMediaAdapter.loadStateFlow,
            action = {
                viewModel.setErrorUiState(it)
            })

        collectLast(viewModel.uiState.value.allMedia, ::setAllMedia)
    }


    private suspend fun setAllMedia(itemsPagingData: PagingData<MediaUiState>) {
        allMediaAdapter.submitData(itemsPagingData)
    }

    private fun collectEvent() {
        collectLast(viewModel.mediaUIEvent) {
            it?.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }

    private fun onEvent(event: MediaUIEvent) {
        when (event) {
            MediaUIEvent.BackEvent -> {
                removeFragment()
            }

            is MediaUIEvent.ClickMovieEvent -> {
                findNavController().navigate(
                    AllMovieFragmentDirections.actionAllMovieFragmentToMovieDetailFragment(
                        event.movieID
                    )
                )
            }

            is MediaUIEvent.ClickSeriesEvent -> {
                findNavController().navigate(
                    AllMovieFragmentDirections.actionAllMovieFragmentToTvShowDetailsFragment(
                        event.seriesID
                    )
                )
            }

            MediaUIEvent.RetryEvent -> {
                allMediaAdapter.retry()
            }
        }
    }

    private fun removeFragment() {
        findNavController().popBackStack()
    }

    private fun getTitle(type: AllMediaType): String {
        return when (type) {
            AllMediaType.ON_THE_AIR -> resources.getString(R.string.title_on_air)
            AllMediaType.AIRING_TODAY -> resources.getString(R.string.title_airing_today)
            AllMediaType.LATEST -> resources.getString(R.string.latest)
            AllMediaType.POPULAR -> resources.getString(R.string.popular)
            AllMediaType.TOP_RATED -> resources.getString(R.string.title_top_rated_tv_show)
            AllMediaType.TRENDING -> resources.getString(R.string.title_trending)
            AllMediaType.RECENTLY_RELEASED -> resources.getString(R.string.title_streaming)
            AllMediaType.UPCOMING -> resources.getString(R.string.title_upcoming)
            AllMediaType.MYSTERY -> resources.getString(R.string.title_mystery)
            AllMediaType.ADVENTURE -> resources.getString(R.string.title_adventure)
            AllMediaType.ACTOR_MOVIES -> ""
            AllMediaType.BASED_ON_TRUE_EVENTS -> resources.getString(R.string.based_on_true_events)
            AllMediaType.CINEMATIC_MASTERPIECE -> resources.getString(R.string.cinematic_masterpieces)
            AllMediaType.FAMILY_NIGHT_PICKS -> resources.getString(R.string.family_night_picks)
            AllMediaType.FEEL_GOOD_PREFERENCES -> resources.getString(R.string.feel_good_favorites)
            AllMediaType.LATE_NIGHT_THRILLS -> resources.getString(R.string.late_night_thrills)
            AllMediaType.MIND_BENDING_STORIES -> resources.getString(R.string.mind_bending_stories)
        }
    }

}