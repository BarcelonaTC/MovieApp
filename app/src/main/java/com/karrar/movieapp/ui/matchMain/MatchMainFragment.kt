package com.karrar.movieapp.ui.matchMain


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentMatchMainBinding
import com.karrar.movieapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchMainFragment : BaseFragment<FragmentMatchMainBinding>() {
    override val layoutIdFragment = R.layout.fragment_match_main
    override val viewModel: MatchMainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_matchMainFragment_to_matchSelectionFragment)
        }
    }

    private fun removeFragment() {
        findNavController().popBackStack()
    }
}