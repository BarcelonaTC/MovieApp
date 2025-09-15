package com.karrar.movieapp.ui.myList.listDetails

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.GestureDetector
import androidx.core.content.ContextCompat
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

        val adapter = ListDetailsAdapter(mutableListOf(), viewModel)
        binding.lists.adapter = adapter

        val swipeCallback = SwipeToDeleteCallback(requireContext()) { position ->
            val vh = binding.lists.findViewHolderForAdapterPosition(position)
            val tag = vh?.itemView?.tag as? Int

            tag?.let {
                viewModel.onDeleteClick(it) }
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.lists)

        binding.lists.setOnTouchListener { _, event ->
            swipeCallback.handleTouchEvent(event)
            false
        }

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

    private class SwipeToDeleteCallback(
        context: Context,
        private val onDeleteClicked: (Int) -> Unit
    ) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        private val paint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.additional_primary_red)
        }
        private val icon = ContextCompat.getDrawable(context, R.drawable.trash)

        private val metrics = context.resources.displayMetrics
        private val verticalMargin = 8f.toPx()
        private val horizontalMargin = 12f.toPx()
        private val radius = 12f.toPx()

        private var currentRect: RectF? = null
        private var tappedViewHolder: RecyclerView.ViewHolder? = null

        private val gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    val rect = currentRect
                    val vh = tappedViewHolder
                    if (rect != null && rect.contains(e.x, e.y) && vh != null) {
                        onDeleteClicked(vh.adapterPosition)
                        return true
                    }
                    return false
                }
            })

        fun handleTouchEvent(event: MotionEvent) {
            gestureDetector.onTouchEvent(event)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // Do nothing here (click will handle delete)
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
            val itemView = viewHolder.itemView
            val drawWidth = minOf(-dX, MAX_WIDTH)

            val rect = RectF(
                itemView.right - drawWidth,
                itemView.top + verticalMargin * 4,
                itemView.right - horizontalMargin,
                itemView.bottom - verticalMargin * 2
            )

            currentRect = rect
            tappedViewHolder = viewHolder

            c.drawRoundRect(rect, radius, radius, paint)
            drawIcon(c, rect)

            val clampedDX = dX.coerceAtLeast((-itemView.width / 4.5f))
            super.onChildDraw(
                c,
                recyclerView,
                viewHolder,
                clampedDX,
                dY,
                actionState,
                isCurrentlyActive
            )
        }

        private fun drawIcon(c: Canvas, rect: RectF) {
            icon?.let {
                val left = (rect.left + rect.right) / 2f - it.intrinsicWidth / 2f
                val top = (rect.top + rect.bottom) / 2f - it.intrinsicHeight / 2f
                it.setBounds(
                    left.toInt(),
                    top.toInt(),
                    (left + it.intrinsicWidth).toInt(),
                    (top + it.intrinsicHeight).toInt()
                )
                it.draw(c)
            }
        }

        private fun Float.toPx() =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, metrics)

        companion object {
            private const val MAX_WIDTH = 200f
        }
    }
}
