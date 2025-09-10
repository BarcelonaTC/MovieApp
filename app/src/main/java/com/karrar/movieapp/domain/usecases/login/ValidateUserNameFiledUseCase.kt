package com.karrar.movieapp.domain.usecases.login

import com.karrar.movieapp.utilities.FormFieldState
import javax.inject.Inject

class ValidateUserNameFiledUseCase @Inject constructor(){
    operator fun invoke(userNameText: String) : FormFieldState {
        val regex = Regex("^[a-zA-Z0-9]+$")
        if(!regex.matches(userNameText)) {
            return FormFieldState.InValid("Usernames can only include letters and numbers")
        }
        return FormFieldState.Valid
    }
}