package com.karrar.movieapp.ui.login

sealed interface LoginUIEvent {
    data class LoginEvent(val login: Int) : LoginUIEvent
    object ForgotPasswordEvent : LoginUIEvent
    object CreateAccountEvent: LoginUIEvent
    object ContinueAsGuestEvent : LoginUIEvent
}