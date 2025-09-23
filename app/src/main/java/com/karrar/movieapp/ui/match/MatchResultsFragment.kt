package com.karrar.movieapp.ui.match

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentMatchResultsBinding
import com.karrar.movieapp.ui.actors.ActorsViewModel
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.ui.main.MainActivity
import com.karrar.movieapp.utilities.hideBottomNav
import com.karrar.movieapp.utilities.showBottomNav
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MatchResultsFragment(
) : BaseFragment<FragmentMatchResultsBinding>() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter: ImageAdapter

    override val layoutIdFragment = R.layout.fragment_match_results
    override val viewModel: ActorsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(false)
        (requireActivity() as MainActivity).hideBottomNav()
        init()
        setUpTransformer()
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun dpToPx(dp: Int) = (dp * resources.displayMetrics.density).toInt()
    private fun setUpTransformer() {
        viewPager2.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            setPadding(dpToPx(60), 0, dpToPx(60), 0)
        }
        (viewPager2.getChildAt(0) as RecyclerView).apply {
            clipToPadding = false
            clipChildren = false
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(dpToPx(1)))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                val scale = 0.83f + r * 0.17f
                page.scaleX = scale
                page.scaleY = scale

                page.translationX =
                    position * dpToPx(60) * -0.75f
                page.translationY = -r * dpToPx(10)
                page.elevation = r * dpToPx(5).toFloat()
                page.alpha = 0.5f + r * 0.5f
            }
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun init() {
        viewPager2 = binding.viewPager2
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()

        imageList.add(R.drawable.poster1)
        imageList.add(R.drawable.poster2)
        imageList.add(R.drawable.poster3)
        imageList.add(R.drawable.poster4)

        adapter = ImageAdapter(imageList, viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }
    private fun removeFragment() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).showBottomNav()
    }
}

