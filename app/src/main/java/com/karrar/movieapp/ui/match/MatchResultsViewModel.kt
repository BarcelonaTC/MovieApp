package com.karrar.movieapp.ui.match

import com.karrar.movieapp.domain.usecases.GetActorsDataUseCase
import com.karrar.movieapp.ui.adapters.ActorsInteractionListener
import com.karrar.movieapp.ui.base.BaseViewModel
import com.karrar.movieapp.ui.mappers.ActorUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchResultsViewModel @Inject constructor(
    private val getActorsDataUseCase: GetActorsDataUseCase,
    private val actorMapper: ActorUiMapper
) : BaseViewModel(), ActorsInteractionListener {
    override fun getData() {
        TODO("Not yet implemented")
    }

    override fun onClickActor(actorID: Int) {
        TODO("Not yet implemented")
    }

}