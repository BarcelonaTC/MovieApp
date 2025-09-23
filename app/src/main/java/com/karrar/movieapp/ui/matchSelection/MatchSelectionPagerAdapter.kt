package com.karrar.movieapp.ui.matchSelection

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MatchSelectionPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MatchMoodQ1()
            1 -> MatchMoodQ2()
            2 -> MatchMoodQ1()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }

}