package com.example.capstoneproject.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {


    suspend fun saveSessions(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[PASSWORD_KEY] = user.password
            preferences[NAME_KEY] = user.name
            preferences[PROFILE_KEY] = user.profile
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                name = preferences[NAME_KEY] ?: "",
                email = preferences[EMAIL_KEY] ?: "",
                password = preferences[PASSWORD_KEY] ?: "",
                token = preferences[TOKEN_KEY] ?: "",
                profile = preferences[PROFILE_KEY] ?: "",
                isLogin = preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun updateName(newName: String) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = newName
        }
    }

    suspend fun updateEmail(newEmail: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = newEmail
        }
    }

    suspend fun updatePassword(newPassword: String) {
        dataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = newPassword
        }
    }

    suspend fun updateProfile(newPicture: String) {
        dataStore.edit { preferences ->
            preferences[PROFILE_KEY] = newPicture
        }
    }

    suspend fun saveCookies(cookies: String) {
        dataStore.edit { preferences ->
            preferences[COOKIE_KEY] = cookies
        }
    }

    fun getCookies(): String? {
        var cookies: String? = null
        runBlocking {
            cookies = dataStore.data
                .map { preferences -> preferences[COOKIE_KEY] }
                .firstOrNull()
        }
        return cookies
    }


    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val NAME_KEY = stringPreferencesKey("name")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val COOKIE_KEY = stringPreferencesKey("cookie")
        private val PROFILE_KEY = stringPreferencesKey("profile")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}