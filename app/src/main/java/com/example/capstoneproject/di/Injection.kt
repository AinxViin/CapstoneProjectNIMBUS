package com.example.capstoneproject.di

import android.content.Context
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.retrofit.ApiConfig
import com.example.capstoneproject.ui.home.DetailWisataViewModelFactory
import com.example.capstoneproject.ui.home.HomeViewModelFactory

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.apiService(pref)
        return UserRepository.getInstance(apiService, pref)
    }

    fun provideViewModelFactory(context: Context): HomeViewModelFactory {
        val userRepository =
            provideRepository(context)
        return HomeViewModelFactory(userRepository)
    }

    fun provideDetailWisataViewModelFactory(context: Context): DetailWisataViewModelFactory {
        val userRepository = provideRepository(context)
        val userPreference = UserPreference.getInstance(context.dataStore)
        return DetailWisataViewModelFactory(userPreference, userRepository)
    }
}