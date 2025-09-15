package com.karrar.movieapp.ui.actorDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentActorDetails2Binding
import com.karrar.movieapp.domain.enums.AllMediaType
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.ui.main.MainActivity
import com.karrar.movieapp.utilities.collectLast
import com.karrar.movieapp.utilities.hideBottomNav
import com.karrar.movieapp.utilities.showBottomNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorDetailsFragment : BaseFragment<FragmentActorDetails2Binding>() {

    override val layoutIdFragment = R.layout.fragment_actor_details_2
    override val viewModel: ActorViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).hideBottomNav()
        setTitle(false)
        binding.actorBestMovies.adapter = ActorMoviesAdapter(mutableListOf(), viewModel)

        collectLast(viewModel.actorDetailsUIEvent) {
            it.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }

    private fun onEvent(event: ActorDetailsUIEvent) {
        when (event) {
            ActorDetailsUIEvent.BackEvent -> {
                removeFragment()
            }

            is ActorDetailsUIEvent.ClickMovieEvent -> {
                seeMovieDetails(event.movieID)
            }

            ActorDetailsUIEvent.SeeAllMovies -> {
                navigateToActorMovies()
            }

            is ActorDetailsUIEvent.ClickSocialMediaLink -> openLink(event.url)
            ActorDetailsUIEvent.SeeAllGallery -> {
                navigateToActorGallery()
            }
        }
    }

    private fun navigateToActorMovies() {
        Navigation.findNavController(binding.root)
            .navigate(
                ActorDetailsFragmentDirections.actionActorDetailsFragmentToAllMovieOfActorFragment(
                    viewModel.args.id,
                    AllMediaType.ACTOR_MOVIES
                )
            )
    }

    private fun navigateToActorGallery() {
        Navigation.findNavController(binding.root)
            .navigate(
                ActorDetailsFragmentDirections.actionActorDetailsFragmentToActorGalleryFragment(
                    viewModel.args.id,
                    viewModel.actorDetailsUIState.value.name
                )
            )
    }

    private fun seeMovieDetails(movieID: Int) {
        findNavController().navigate(
            ActorDetailsFragmentDirections.actionActorDetailsFragmentToMovieDetailFragment(
                movieID
            )
        )
    }

    private fun removeFragment() {
        findNavController().popBackStack()
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).showBottomNav()
    }

}