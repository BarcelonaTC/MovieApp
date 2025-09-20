package com.karrar.movieapp.ui.profile

sealed interface ProfileUIEvent {
    object LoginEvent : ProfileUIEvent
    object RatedMoviesEvent : ProfileUIEvent
    object WatchHistoryEvent : ProfileUIEvent
    object EditProfileEvent : ProfileUIEvent
    object LogOutBottomSheetEvent : ProfileUIEvent
}
