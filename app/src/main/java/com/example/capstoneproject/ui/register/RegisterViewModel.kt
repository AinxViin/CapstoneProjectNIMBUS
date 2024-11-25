package com.example.capstoneproject.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(
        nama: String,
        username: String,
        email: String,
        password: String,
        password_confirm: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = userRepository.register(nama, username, email, password, password_confirm)
                onResult(true, "Pendaftaran berhasil! ID pengguna: ${response.id}")
            } catch (e: Exception) {
                onResult(false, e.message ?: "Pendaftaran gagal, silakan coba lagi.")
            }
        }
    }
}
