package com.example.capstoneproject.ui.explore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.response.RandomPlacesItem
import com.example.capstoneproject.response.RecommendationRequest
import kotlinx.coroutines.launch

class RecommendationViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _provinceId = MutableLiveData<Int?>(null)
    val provinceId: LiveData<Int?> get() = _provinceId

    private val _categoryId = MutableLiveData<Int?>(null)
    val categoryId: LiveData<Int?> get() = _categoryId

    private val _recommendations = MutableLiveData<List<RandomPlacesItem>>()
    val recommendations: LiveData<List<RandomPlacesItem>> = _recommendations

    fun setProvinceId(id: Int?) {
        _provinceId.value = id
    }

    fun setCategoryId(id: Int?) {
        _categoryId.value = id
    }

    fun getRecommendation(request: RecommendationRequest) {
        viewModelScope.launch {
            try {
                val response = userRepository.getRecommendations(request)
                _recommendations.value = response.randomPlaces
            } catch (e: Exception) {
                Log.e("RecommendationError", e.message ?: "Unknown error")
            }
        }
    }

}

