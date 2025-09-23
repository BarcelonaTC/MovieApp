package com.karrar.movieapp.ui.matchMain

import com.karrar.movieapp.ui.base.BaseInteractionListener
import com.karrar.movieapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchMainViewModel @Inject constructor() : BaseViewModel(),
    BaseInteractionListener {
    override fun getData() {
        TODO("Not yet implemented")
    }
}