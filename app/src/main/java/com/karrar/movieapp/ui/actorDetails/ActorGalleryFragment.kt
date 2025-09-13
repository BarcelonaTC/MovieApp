package com.karrar.movieapp.ui.actorDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentActorGalleryBinding
import com.karrar.movieapp.ui.adapters.GalleryAdapter
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.ui.main.MainActivity
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
        binding.arrowBack.setOnClickListener {
            removeFragment()
        }
        binding.galleryRV.adapter =
            GalleryAdapter(
                items = mutableListOf(),
                viewModel
            )
    }

    private fun removeFragment() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).showBottomNav()
    }
}