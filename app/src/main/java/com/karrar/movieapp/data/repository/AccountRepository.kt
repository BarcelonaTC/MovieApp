package com.karrar.movieapp.data.repository

import com.karrar.movieapp.data.remote.response.account.AccountDto


interface AccountRepository {

    fun getSessionId(): String?


    suspend fun loginWithUserNameANdPassword(userName: String, password: String) : Boolean

    suspend fun logout()

    suspend fun getAccountDetails(): AccountDto?

    suspend fun saveTheme(value: Boolean)

    fun isDarkTheme(): Boolean?
}

