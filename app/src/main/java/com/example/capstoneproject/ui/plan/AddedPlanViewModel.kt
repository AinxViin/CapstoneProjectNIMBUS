package com.example.capstoneproject.ui.plan

import androidx.lifecycle.*
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.response.PlanResponse
import kotlinx.coroutines.launch

class AddedPlanViewModel(private val userRepository: UserRepository) : ViewModel() {

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

    suspend fun getUserId(): Int {
        val session = userRepository.getUserSession()
        return session?.id ?: 0
    }
}

