package com.karrar.movieapp.ui.home

import androidx.lifecycle.viewModelScope
import com.karrar.movieapp.domain.enums.AllMediaType
import com.karrar.movieapp.domain.enums.HomeItemsType
import com.karrar.movieapp.domain.usecase.home.HomeUseCasesContainer
import com.karrar.movieapp.domain.usecases.CheckIfLoggedInUseCase
import com.karrar.movieapp.domain.usecases.GetAccountDetailsUseCase
import com.karrar.movieapp.domain.usecases.mylist.GetMyListUseCase
import com.karrar.movieapp.ui.adapters.FeaturedCollectionType
import com.karrar.movieapp.ui.adapters.FeaturedCollectionsInteractionListener
import com.karrar.movieapp.ui.adapters.MovieInteractionListener
import com.karrar.movieapp.ui.adapters.SeriesInteractionListener
import com.karrar.movieapp.ui.base.BaseViewModel
import com.karrar.movieapp.ui.home.adapter.YourCollectionsInteractionListener
import com.karrar.movieapp.ui.home.homeUiState.HomeUIEvent
import com.karrar.movieapp.ui.home.homeUiState.HomeUiState
import com.karrar.movieapp.ui.mappers.MediaUiMapper
import com.karrar.movieapp.ui.models.TypeOfMedia
import com.karrar.movieapp.ui.myList.CreatedListUIMapper
import com.karrar.movieapp.ui.myList.myListUIState.CreatedListUIState
import com.karrar.movieapp.ui.profile.AccountUIStateMapper
import com.karrar.movieapp.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCasesContainer: HomeUseCasesContainer,
    private val mediaUiMapper: MediaUiMapper,
    private val popularUiMapper: PopularUiMapper,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val accountUIStateMapper: AccountUIStateMapper,
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase,
    private val getMyListUseCase: GetMyListUseCase,
    private val createdListUIMapper: CreatedListUIMapper
) : BaseViewModel(), HomeInteractionListener, MovieInteractionListener, SeriesInteractionListener,
    YourCollectionsInteractionListener, FeaturedCollectionsInteractionListener {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    private val _homeUIEvent = MutableStateFlow<Event<HomeUIEvent?>>(Event(null))
    val homeUIEvent = _homeUIEvent.asStateFlow()

    init {
        getHomeData()
    }

    override fun getData() {
        _homeUiState.update { it.copy(error = emptyList()) }
        getHomeData()
    }

    private fun getHomeData() {
        _homeUiState.update { it.copy(isLoading = true) }
        getUserName()
        getPopularMovies()
        getRecentlyReleased()
        getUpcomingMovies()
        getTopRatedTvShow()
        getYourCollections()
    }


    private fun getUserName() {
        if (checkIfLoggedInUseCase()) {
            viewModelScope.launch {
                try {
                    val accountDetails = accountUIStateMapper.map(getAccountDetailsUseCase())
                    _homeUiState.update {
                        it.copy(
                            userName = accountDetails.username,
                            isLoading = false
                        )
                    }
                } catch (throwable: Throwable) {
                    onError(throwable.message.toString())
                }
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            try {
                homeUseCasesContainer.getPopularMoviesUseCase().collect { list ->
                    if (list.isNotEmpty()) {
                        val items = list.map(popularUiMapper::map)
                        _homeUiState.update {
                            it.copy(
                                popularMovies = HomeItem.Slider(items),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (th: Throwable) {
                onError(th.message.toString())
            }
        }
    }

    private fun getRecentlyReleased() {
        viewModelScope.launch {
            try {
                homeUseCasesContainer.getNowStreamingMoviesUseCase().collect { list ->
                    if (list.isNotEmpty()) {
                        val items = list.map(mediaUiMapper::map)
                        _homeUiState.update {
                            it.copy(
                                recentlyReleased = HomeItem.RecentlyReleased(items),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (th: Throwable) {
                onError(th.message.toString())
            }
        }

    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            try {
                homeUseCasesContainer.getUpcomingMoviesUseCase().collect { list ->
                    if (list.isNotEmpty()) {
                        val items = list.map(mediaUiMapper::map)
                        _homeUiState.update {
                            it.copy(
                                upcomingMovies = HomeItem.UpcomingMovies(items),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (th: Throwable) {
                onError(th.message.toString())
            }
        }


    }

    private fun getTopRatedTvShow() {
        viewModelScope.launch {
            try {
                homeUseCasesContainer.getTopRatedTvShowUseCase().collect { list ->
                    if (list.isNotEmpty()) {
                        val items = list.map(mediaUiMapper::map)
                        _homeUiState.update {
                            it.copy(
                                topRatedTvShows = HomeItem.TopRatedTvShows(items),
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (th: Throwable) {
                onError(th.message.toString())
            }
        }
    }

    private fun getYourCollections() {
        viewModelScope.launch {
            try {
                val list = getMyListUseCase().map { createdListUIMapper.map(it) }
                _homeUiState.update {
                    it.copy(isLoading = false, yourCollections = HomeItem.YourCollections(list))
                }
            } catch (th: Throwable) {
                onError(th.message.toString())
            }
        }
    }

    private fun onError(message: String) {
        val errors = _homeUiState.value.error.toMutableList()
        errors.add(message)
        _homeUiState.update { it.copy(error = errors, isLoading = false) }
    }

    override fun onClickMovie(movieId: Int, typeOfMedia: TypeOfMedia) {
        if (typeOfMedia.name == TypeOfMedia.MOVIE.name)
            _homeUIEvent.update { Event(HomeUIEvent.ClickMovieEvent(movieId)) }
        else
            _homeUIEvent.update { Event(HomeUIEvent.ClickSeriesEvent(movieId)) }
    }


    override fun onClickSeeAllMovie(homeItemsType: HomeItemsType) {
        val type = when (homeItemsType) {
            HomeItemsType.RECENTLY_RELEASED -> AllMediaType.RECENTLY_RELEASED
            HomeItemsType.UPCOMING_MOVIES -> AllMediaType.UPCOMING
            HomeItemsType.TOP_RATED_TV_SHOWS -> AllMediaType.TOP_RATED
            HomeItemsType.NON -> AllMediaType.RECENTLY_RELEASED
            HomeItemsType.YOUR_COLLECTIONS -> AllMediaType.RECENTLY_RELEASED
        }
        _homeUIEvent.update { Event(HomeUIEvent.ClickSeeAllMovieEvent(type)) }
    }

    override fun onClickSeries(seriesId: Int) {
        _homeUIEvent.update { Event(HomeUIEvent.ClickSeriesEvent(seriesId)) }
    }

    override fun onClickSeeAllSeries(homeItemsType: HomeItemsType) {
        _homeUIEvent.update { Event(HomeUIEvent.ClickSeeAllTVShowsEvent(AllMediaType.TOP_RATED)) }
    }

    override fun onClickExploreCTA() {
    }

    override fun onClickMatchCTA() {
    }

    override fun onClickSeeAllYourCollections() {
        _homeUIEvent.update { Event(HomeUIEvent.ClickSeeAllYourCollection) }
    }

    override fun onListClick(item: CreatedListUIState) {
        _homeUIEvent.update { Event(HomeUIEvent.ClickCollection(item)) }
    }

    override fun onClickItem(featuredCollectionType: FeaturedCollectionType) {
        when (featuredCollectionType) {
            FeaturedCollectionType.LATE_NIGHT_THRILLS -> _homeUIEvent.update {
                Event(
                    HomeUIEvent.ClickFeaturedCollectionEvent(
                        AllMediaType.LATE_NIGHT_THRILLS
                    )
                )
            }

            FeaturedCollectionType.FAMILY_NIGHT_PICKS -> _homeUIEvent.update {
                Event(
                    HomeUIEvent.ClickFeaturedCollectionEvent(
                        AllMediaType.FAMILY_NIGHT_PICKS
                    )
                )
            }

            FeaturedCollectionType.MIND_BENDING_STORIES -> _homeUIEvent.update {
                Event(
                    HomeUIEvent.ClickFeaturedCollectionEvent(
                        AllMediaType.MIND_BENDING_STORIES
                    )
                )
            }

            FeaturedCollectionType.BASED_ON_TRUE_EVENTS -> _homeUIEvent.update {
                Event(
                    HomeUIEvent.ClickFeaturedCollectionEvent(
                        AllMediaType.BASED_ON_TRUE_EVENTS
                    )
                )
            }

            FeaturedCollectionType.CINEMATIC_MASTERPIECE -> _homeUIEvent.update {
                Event(
                    HomeUIEvent.ClickFeaturedCollectionEvent(
                        AllMediaType.CINEMATIC_MASTERPIECE
                    )
                )
            }

            FeaturedCollectionType.FEEL_GOOD_PREFERENCES -> _homeUIEvent.update {
                Event(
                    HomeUIEvent.ClickFeaturedCollectionEvent(
                        AllMediaType.FEEL_GOOD_PREFERENCES
                    )
                )
            }
        }
    }
}