package com.example.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.response.DetailWisataResponse
import com.example.capstoneproject.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailWisataViewModel(private val userPreference: UserPreference) : ViewModel() {

    private val _detailWisata = MutableLiveData<DetailWisataResponse>()
    val detailWisata: LiveData<DetailWisataResponse> get() = _detailWisata

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val apiService = ApiConfig.apiService(userPreference)

    fun fetchDetailWisata(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response: Response<DetailWisataResponse> = apiService.getDetailWisata(id)
                if (response.isSuccessful) {
                    _detailWisata.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}