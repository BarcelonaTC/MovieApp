package com.karrar.movieapp.ui.myList.listDetails

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentListDetailsBinding
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.ui.myList.listDetails.listDetailsUIState.ListDetailsUIEvent
import com.karrar.movieapp.utilities.Constants
import com.karrar.movieapp.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListDetailsFragment : BaseFragment<FragmentListDetailsBinding>() {
    override val layoutIdFragment = R.layout.fragment_list_details
    override val viewModel: ListDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true, viewModel.args.listName)
        binding.lists.adapter = ListDetailsAdapter(mutableListOf(), viewModel)

        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {

            }


            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val clampedDX = dX.coerceAtLeast((-viewHolder.itemView.width / 6).toFloat())
                super.onChildDraw(c, recyclerView, viewHolder, clampedDX, dY, actionState, isCurrentlyActive)
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.lists)

        binding.lists.adapter = ListDetailsAdapter(mutableListOf(), viewModel)
        collectLast(viewModel.listDetailsUIEvent) {
            it.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }

    private fun onEvent(event: ListDetailsUIEvent) {
        if (event is ListDetailsUIEvent.OnItemSelected) {
            if (event.savedMediaUIState.mediaType == Constants.MOVIE) {
                navigateToMovieDetails(event.savedMediaUIState.mediaID)
            } else {
                navigateToTvShowDetails(event.savedMediaUIState.mediaID)
            }
        }
    }

    private fun navigateToMovieDetails(id: Int) {
        findNavController().navigate(
            ListDetailsFragmentDirections.actionSavedListFragmentToMovieDetailFragment(id)
        )
    }

    private fun navigateToTvShowDetails(id: Int) {
        findNavController().navigate(
            ListDetailsFragmentDirections.actionListDetailsFragmentToTvShowDetailsFragment(id)
        )
    }

}