package com.karrar.movieapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.karrar.movieapp.data.repository.AccountRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MovieApplication : Application() {

    @Inject
    lateinit var repository: AccountRepository

    override fun onCreate() {
        super.onCreate()
        updateAppTheme()
    }

    private fun updateAppTheme(){
        repository.isDarkTheme()?.let { isDark ->
            if (isDark)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}