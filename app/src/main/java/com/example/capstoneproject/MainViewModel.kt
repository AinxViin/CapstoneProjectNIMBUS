package com.example.capstoneproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.request.LoginRequest
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun login(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                // Create LoginRequest object
                val loginRequest = LoginRequest(email, password)

                // Call login function from repository
                val response = userRepository.login(loginRequest)

                // Since the response now only has a message, we directly use it
                val userModel = UserModel(
                    email = email,  // Store the email in session
                    token = "temporary_token",  // Temporary token or use another method if needed
                    isLogin = true
                )
                userRepository.saveSession(userModel)
                onResult(true, response.message)
            } catch (e: Exception) {
                onResult(false, e.message ?: "Login failed")
            }
        }
    }
}
