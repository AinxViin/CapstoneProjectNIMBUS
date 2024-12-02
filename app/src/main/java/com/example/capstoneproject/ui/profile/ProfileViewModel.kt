package com.example.capstoneproject.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.request.LoginRequest
import com.example.capstoneproject.request.UpdateRequest
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun updateInfo(updateRequest: UpdateRequest, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.updateUser(updateRequest)

                onResult(true, "Update data berhasil")
            } catch (e: Exception) {
                onResult(false, e.message ?: "Update gagal")
            }
        }
    }
}