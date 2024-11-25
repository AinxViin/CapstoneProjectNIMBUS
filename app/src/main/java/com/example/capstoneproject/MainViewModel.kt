package com.example.capstoneproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _session = MutableLiveData<UserModel>()
    val session: LiveData<UserModel> get() = _session

    fun getSession() {
        viewModelScope.launch {
            userRepository.getSession().collect { user ->
                _session.value = user
            }
        }
    }

    fun login(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.login(email, password)
                val loginResult = response.loginResult
                if (loginResult != null) {
                    val userModel = UserModel(
                        email = loginResult.userId,
                        token = loginResult.token,
                        isLogin = true
                    )
                    userRepository.saveSession(userModel)
                    onResult(true, response.message)
                } else {
                    onResult(false, "Login failed")
                }
            } catch (e: Exception) {
                onResult(false, e.message ?: "Login failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
