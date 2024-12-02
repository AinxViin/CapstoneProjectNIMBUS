package com.example.capstoneproject.di

import android.content.Context
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.apiService(pref)
        return UserRepository.getInstance(apiService, pref)
    }
}