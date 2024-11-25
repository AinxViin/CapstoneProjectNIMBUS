package com.example.capstoneproject.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun login(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.login(email, password)
                val loginResult = response.loginResult
                val userModel = UserModel(
                    email = loginResult?.userId ?: "",
                    token = loginResult?.token ?: "",
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