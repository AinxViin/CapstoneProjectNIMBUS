package com.example.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.response.PlanResponse
import com.example.capstoneproject.retrofit.ApiService
import kotlinx.coroutines.launch

class AddWisataViewModel(private val apiService: ApiService) : ViewModel() {

    private val _plans = MutableLiveData<List<PlanResponse>>()
    val plans: LiveData<List<PlanResponse>> get() = _plans

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getPlans() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.getPlans()
                _plans.value = response
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = e.message ?: "Error fetching plans"
            }
        }
    }
}
