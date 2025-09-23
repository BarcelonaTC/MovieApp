package com.karrar.movieapp.ui.matchSelection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.MatchMoodQ1Binding
import com.karrar.movieapp.databinding.MatchMoodQ2Binding
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.ui.main.MainActivity
import com.karrar.movieapp.utilities.GridSpacingItemDecoration
import com.karrar.movieapp.utilities.hideBottomNav
import kotlin.getValue

class MatchMoodQ2 : BaseFragment<MatchMoodQ2Binding>() {
    override val layoutIdFragment = R.layout.match_mood_q2
    override val viewModel: MatchSelectionViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).hideBottomNav()

        val spanCount = 3
        val spacingDp = 12
        val spacingPx = (spacingDp * resources.displayMetrics.density).toInt()
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter = GenreAdapter(
            items = mutableListOf(),
            viewModel
        )

        binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacingPx))
        binding.recyclerView.adapter = GenreAdapter(items = mutableListOf(), viewModel)
    }
}