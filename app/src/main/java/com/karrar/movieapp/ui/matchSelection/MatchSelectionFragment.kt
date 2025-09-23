package com.karrar.movieapp.ui.matchSelection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentMatchSelectionBinding
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.ui.main.MainActivity
import com.karrar.movieapp.utilities.hideBottomNav
import com.karrar.movieapp.utilities.showBottomNav

class MatchSelectionFragment : BaseFragment<FragmentMatchSelectionBinding>() {
    override val layoutIdFragment = R.layout.fragment_match_selection
    override val viewModel: MatchSelectionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).hideBottomNav()
        val viewPager = binding.viewPager
        val adapter = MatchSelectionPagerAdapter(this)
        binding.viewPager.adapter = adapter
        viewPager.isUserInputEnabled = true
        (viewPager.getChildAt(0) as RecyclerView).overScrollMode = View.OVER_SCROLL_NEVER
        val progressBar = binding.progressBar
        val nextButton = binding.button

        progressBar.max = adapter.itemCount

        nextButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                viewPager.currentItem = currentItem + 1
            } else {

            }
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                progressBar.progress = position + 1
            }
        })
    }

    private fun removeFragment() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).showBottomNav()
    }
}