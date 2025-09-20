package com.karrar.movieapp.ui.profile

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.karrar.movieapp.data.repository.AccountRepository
import com.karrar.movieapp.domain.usecases.CheckIfLoggedInUseCase
import com.karrar.movieapp.domain.usecases.GetAccountDetailsUseCase
import com.karrar.movieapp.ui.base.BaseViewModel
import com.karrar.movieapp.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val accountUIStateMapper: AccountUIStateMapper,
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase,
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    private val _profileDetailsUIState = MutableStateFlow(ProfileUIState())
    val profileDetailsUIState = _profileDetailsUIState.asStateFlow()

    private val _profileUIEvent: MutableStateFlow<Event<ProfileUIEvent?>> = MutableStateFlow(Event(null))
    val profileUIEvent= _profileUIEvent.asStateFlow()

    private val _isDarkMode = MutableStateFlow(accountRepository.isDarkTheme() == true)
    val isDarkMode = _isDarkMode.asStateFlow()

    init {
        getData()
    }

    override fun getData() {
        getProfileDetails()
    }

    private fun getProfileDetails() {
        if (checkIfLoggedInUseCase().not()) {
            _profileDetailsUIState.update {
                it.copy(isLoading = true, isLoggedIn = true, error = false)
            }

            viewModelScope.launch {
                try {
                    val accountDetails = accountUIStateMapper.map(getAccountDetailsUseCase())
                    _profileDetailsUIState.update {
                        it.copy(
                            avatarPath = accountDetails.avatarPath,
                            name = accountDetails.name,
                            username = accountDetails.username,
                            isLoading = false
                        )
                    }
                } catch (t: Throwable) {
                    _profileDetailsUIState.update {
                        it.copy(isLoading = false, error = true)
                    }
                }
            }
        } else {
            _profileDetailsUIState.update {
                it.copy(isLoggedIn = false)
            }
        }
    }

    fun onClickRatedMovies() {
        _profileUIEvent.update { Event(ProfileUIEvent.RatedMoviesEvent) }
    }

    fun showLogOutBottomSheet(){
        _profileUIEvent.update { Event(ProfileUIEvent.LogOutBottomSheetEvent) }
    }
    fun onClickLogout() {
        wrapWithState(
            function = {
                accountRepository.logout()
            },
            errorFunction = {
                _profileDetailsUIState.update {
                    it.copy(error = true)
                }
            }
        )
    }

    fun onClickWatchHistory() {
        _profileUIEvent.update { Event(ProfileUIEvent.WatchHistoryEvent) }
    }

    fun onClickLogin() {
        _profileUIEvent.update { Event(ProfileUIEvent.LoginEvent) }
    }

    fun onClickEditProfile(){
        _profileUIEvent.update { Event(ProfileUIEvent.EditProfileEvent) }
    }

    fun onThemeSwitchChanged(isChecked: Boolean){
        wrapWithState(
            function = {
                accountRepository.saveTheme(isChecked)
                AppCompatDelegate.setDefaultNightMode(
                    if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                )
                _isDarkMode.update { isChecked }
            },
            errorFunction = {
                _profileDetailsUIState.update {
                    it.copy(error = true)
                }
            }
        )

    }
}