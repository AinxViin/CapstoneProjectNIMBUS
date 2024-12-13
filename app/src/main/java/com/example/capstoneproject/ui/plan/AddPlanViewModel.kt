package com.example.capstoneproject.ui.plan

import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.request.AddPlanRequest

class AddPlanViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun addPlan(planName: String) {
        val addPlanRequest = AddPlanRequest(planName)

        userRepository.addPlan(addPlanRequest)
    }
}
