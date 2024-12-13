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
                val loginRequest = LoginRequest(email, password)

                val response = userRepository.login(loginRequest)

                val userModel = UserModel(
                    email = email,
                    token = "temporary_token",
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
