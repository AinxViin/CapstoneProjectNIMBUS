package com.example.capstoneproject.ui.plan

import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.response.PlanDestinationResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailPlanViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getDestinations(planId: Int): Flow<List<PlanDestinationResponse>> = flow {
        val destinations = userRepository.getPlanDestinations(planId)
        emit(destinations)
    }
    suspend fun deleteDestination(planId: Int, wisataId: Int): Boolean {
        return userRepository.deleteDestination(planId, wisataId)
    }
}
