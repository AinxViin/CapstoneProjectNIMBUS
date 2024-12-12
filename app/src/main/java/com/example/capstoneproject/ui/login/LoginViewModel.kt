package com.example.capstoneproject.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.request.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun login(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                // Langkah 1: Login
                val loginRequest = LoginRequest(email, password)
                val loginResponse = userRepository.login(loginRequest)

                // Langkah 2: Fetch user data after login is successful
                val userDetails = fetchUserDetails(password)

                // Langkah 3: Save user session
                userRepository.saveSession(userDetails)

                // Notify success after session is saved
                onResult(true, loginResponse.message)
            } catch (e: Exception) {
                // Notify failure
                onResult(false, e.message ?: "Login failed")
            }
        }
    }

    private suspend fun fetchUserDetails(password: String): UserModel {
        return try {
            val response = userRepository.getUser()

            // Construct user model from API response
            UserModel(
                email = response.email ?: "",
                name = response.nama ?: "",
                password = password,
                profile = response.profilePic ?: "",
                isLogin = true
            )
        } catch (e: Exception) {
            throw Exception("Failed to fetch user details: ${e.message}", e)
        }
    }
}
