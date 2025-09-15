package com.karrar.movieapp.domain.usecases.login

import javax.inject.Inject

class ValidateLoginFormUseCase @Inject constructor(
    private val validateUserNameFiledUseCase: ValidateUserNameFiledUseCase,
    private val validatePasswordFiledUseCase: ValidatePasswordFiledUseCase,
){

    operator fun invoke(userName:String,password:String):Boolean{
        return validateUserNameFiledUseCase(userName).isValid() && validatePasswordFiledUseCase(password).isValid()
    }
}