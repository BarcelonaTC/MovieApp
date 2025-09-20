package com.karrar.movieapp.data.local


import javax.inject.Inject

interface AppConfiguration {

    fun getSessionId(): String?

    suspend fun saveSessionId(value: String)

    suspend fun saveRequestDate(key: String,value: Long)

    suspend fun getRequestDate(key: String): Long?

    suspend fun saveTheme(value: Boolean)

    fun isDarkTheme(): Boolean?

}

class AppConfigurator @Inject constructor(private val dataStorePreferences: DataStorePreferences) :
    AppConfiguration {

    override fun getSessionId(): String? {
        return dataStorePreferences.readString(SESSION_ID_KEY)
    }

    override suspend fun saveSessionId(value: String) {
        dataStorePreferences.writeString(SESSION_ID_KEY, value)
    }

    override suspend fun saveRequestDate(key: String, value: Long) {
        dataStorePreferences.writeLong(key, value)
    }

    override suspend fun getRequestDate(key: String): Long? {
        return dataStorePreferences.readLong(key)
    }

    override suspend fun saveTheme(value: Boolean) {
        dataStorePreferences.writeBoolean(IS_DARK_THEME_KEY, value)
    }

    override fun isDarkTheme(): Boolean? {
        return dataStorePreferences.readBoolean(IS_DARK_THEME_KEY)
    }


    companion object DataStorePreferencesKeys {
        const val SESSION_ID_KEY = "session_id"
        const val IS_DARK_THEME_KEY = "is_dark_theme"
    }
}