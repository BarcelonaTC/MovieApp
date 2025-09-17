package com.karrar.movieapp.ui.actorGallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.karrar.movieapp.domain.usecases.GetActorImagesUseCase
import com.karrar.movieapp.ui.actorDetails.ActorDetailsUIEvent
import com.karrar.movieapp.ui.actorDetails.Error
import com.karrar.movieapp.ui.adapters.GalleryAdapter
import com.karrar.movieapp.ui.base.BaseViewModel
import com.karrar.movieapp.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorGalleryViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getActorImagesUseCase: GetActorImagesUseCase,
    private val actorGalleryUiMapper: ActorGalleryUiMapper
) : BaseViewModel(), GalleryAdapter.GalleryInteractionListener {
    val args = ActorGalleryFragmentArgs.fromSavedStateHandle(state)
    private val _actorGalleryUIState = MutableStateFlow(ActorGalleryUiState())
    val actorDetailsUIState = _actorGalleryUIState.asStateFlow()
    private val _actorGalleryUIEvent: MutableStateFlow<Event<ActorGalleryUIEvent?>> =
        MutableStateFlow(Event(null))
    val actorGalleryUIEvent = _actorGalleryUIEvent.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        _actorGalleryUIState.update { it.copy(isLoading = true, error = emptyList()) }
        viewModelScope.launch {
            try {
                val actorGalleryUiState = actorGalleryUiMapper.map(getActorImagesUseCase(args.id))
                _actorGalleryUIState.update {
                    it.copy(
                        name = args.name,
                        galleryGroups = actorGalleryUiState.galleryGroups,
                        isLoading = false,
                        isSuccess = true
                    )
                }
            } catch (e: Exception) {
                onError(e.message.toString())
            }
        }
    }

    private fun onError(message: String) {
        _actorGalleryUIState.update { actorDetailsUIState ->
            actorDetailsUIState.copy(
                isLoading = false,
                error = listOf(Error(message)),
            )
        }
    }

    override fun onClickBack() {
        _actorGalleryUIEvent.update { Event(ActorGalleryUIEvent.BackEvent) }
    }

}