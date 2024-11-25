package com.example.capstoneproject.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(name: String, email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.register(name, email, password)

                onResult(true, response.message)
            } catch (e: Exception) {
                onResult(false, e.message ?: "Registration failed")
            }
        }
    }
}
