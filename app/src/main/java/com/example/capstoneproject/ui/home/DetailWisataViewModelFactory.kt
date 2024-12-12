package com.example.capstoneproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserPreference

class DetailWisataViewModelFactory(
    private val userPreference: UserPreference,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailWisataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailWisataViewModel(userPreference, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
