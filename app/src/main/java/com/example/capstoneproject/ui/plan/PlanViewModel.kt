package com.example.capstoneproject.ui.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.response.PlanResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Mendeklarasikan StateFlow dengan tipe yang benar
    private val _plans = MutableStateFlow<List<PlanResponse>>(emptyList())
    val plans: StateFlow<List<PlanResponse>> get() = _plans

    fun fetchPlans() {
        viewModelScope.launch {
            try {
                // Mendapatkan daftar rencana dari repository
                val planList = userRepository.getUserPlans()
                _plans.value = planList
            } catch (e: Exception) {
                // Mengatur nilai _plans menjadi daftar kosong jika terjadi kesalahan
                _plans.value = emptyList()
            }
        }
    }
}
