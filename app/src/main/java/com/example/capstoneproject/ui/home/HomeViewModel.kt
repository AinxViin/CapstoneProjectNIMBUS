package com.example.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.response.ProvinceResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _provinces = MutableLiveData<List<ProvinceResponse>>()
    val provinces: LiveData<List<ProvinceResponse>> = _provinces

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getProvinces() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.getProvinces()
                _provinces.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error fetching provinces"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
