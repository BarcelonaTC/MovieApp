package com.karrar.movieapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.karrar.movieapp.data.repository.AccountRepository
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class MovieApplication : Application() {

    @Inject
    lateinit var repository: AccountRepository

    override fun onCreate() {
        super.onCreate()
        updateAppTheme()
        updateAppLanguage()
    }

    private fun updateAppLanguage() {

        repository.getLanguage()?.let { languageCode ->
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }

    private fun updateAppTheme() {
        repository.isDarkTheme()?.let { isDark ->
            if (isDark)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}