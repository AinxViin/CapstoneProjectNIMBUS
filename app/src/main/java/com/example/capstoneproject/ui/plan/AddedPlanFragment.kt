package com.example.capstoneproject.ui.plan

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.adapter.PlanAdapter
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentAddedPlanBinding
import com.example.capstoneproject.retrofit.ApiConfig
import com.example.capstoneproject.response.PlanResponse
import kotlinx.coroutines.launch

class AddedPlanFragment : Fragment() {

    private var _binding: FragmentAddedPlanBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PlanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddedPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inisialisasi adapter
        adapter = PlanAdapter()
        binding.rvPlan.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlan.adapter = adapter

        fetchPlans() // Panggil fungsi untuk mengambil data rencana
    }

    private fun fetchPlans() {
        // Panggil API untuk mendapatkan daftar rencana
        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.apiService(UserPreference.getInstance(requireContext().dataStore))
                val plans = apiService.getPlans() // Ambil data rencana
                showPlans(plans)
            } catch (e: Exception) {
                Log.e("AddedPlanFragment", "Error fetching plans: ${e.message}")
            }
        }
    }

    private fun showPlans(plans: List<PlanResponse>) {
        // Mengirimkan data ke adapter
        adapter.submitList(plans)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
