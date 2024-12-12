package com.example.capstoneproject.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.request.RegisterRequest
import com.example.capstoneproject.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(
        nama: String,
        username: String,
        email: String,
        password: String,
        password_confirm: String,
        callback: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val registerRequest =
                    RegisterRequest(nama, username, email, password, password_confirm)

                val response: RegisterResponse = userRepository.register(registerRequest)

                if (response.id > 0) {
                    callback(true, "Registrasi berhasil")
                } else {
                    callback(false, "Registrasi gagal")
                }
            } catch (e: Exception) {
                callback(false, e.message ?: "Terjadi kesalahan")
            }
        }
    }
}
