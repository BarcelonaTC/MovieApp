package com.karrar.movieapp.ui.profile

sealed interface ProfileUIEvent {
    object LoginEvent : ProfileUIEvent
    object RatedMoviesEvent : ProfileUIEvent
    object WatchHistoryEvent : ProfileUIEvent
    object EditProfileEvent : ProfileUIEvent
    object LogOutBottomSheetEvent : ProfileUIEvent
    object LogoutEvent : ProfileUIEvent
    object LanguageBottomSheetEvent : ProfileUIEvent
    class LanguageEvent(val languageCode: String) : ProfileUIEvent
}
