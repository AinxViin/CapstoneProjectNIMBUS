package com.example.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.response.PlanResponse
import kotlinx.coroutines.launch

class SelectPlanViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getPlans(userId: Int): LiveData<List<PlanResponse>> {
        val plansLiveData = MutableLiveData<List<PlanResponse>>()
        viewModelScope.launch {
            try {
                val plans = userRepository.getUserPlans()
                val filteredPlans = plans.filter { it.user_id == userId }
                plansLiveData.postValue(filteredPlans)
            } catch (e: Exception) {
                plansLiveData.postValue(emptyList())
            }
        }
        return plansLiveData
    }

    suspend fun getUserID(): Int {
        val session = userRepository.getUserSession()
        return session?.id ?: 0
    }
}