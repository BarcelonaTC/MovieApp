package com.karrar.movieapp.ui.actorGallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentActorGalleryBinding
import com.karrar.movieapp.ui.actorDetails.ActorDetailsUIEvent
import com.karrar.movieapp.ui.adapters.GalleryAdapter
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.ui.main.MainActivity
import com.karrar.movieapp.utilities.collectLast
import com.karrar.movieapp.utilities.hideBottomNav
import com.karrar.movieapp.utilities.showBottomNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorGalleryFragment : BaseFragment<FragmentActorGalleryBinding>() {

    override val layoutIdFragment = R.layout.fragment_actor_gallery
    override val viewModel: ActorGalleryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).hideBottomNav()
        setTitle(false)
        binding.galleryRV.adapter = GalleryAdapter(
            items = mutableListOf(), viewModel
        )
        collectLast(viewModel.actorGalleryUIEvent) {
            it.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }

    private fun onEvent(event: ActorGalleryUIEvent) {
        when (event) {
            ActorGalleryUIEvent.BackEvent -> {
                removeFragment()
            }
        }
    }

    private fun removeFragment() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).showBottomNav()
    }
}