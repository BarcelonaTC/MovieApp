package com.karrar.movieapp.ui.matchSelection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.MatchMoodQ1Binding
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.ui.main.MainActivity
import com.karrar.movieapp.utilities.hideBottomNav
import kotlin.getValue

class MatchMoodQ1 : BaseFragment<MatchMoodQ1Binding>() {
    override val layoutIdFragment = R.layout.match_mood_q1
    override val viewModel: MatchSelectionViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).hideBottomNav()
        binding.listener = viewModel
    }
}