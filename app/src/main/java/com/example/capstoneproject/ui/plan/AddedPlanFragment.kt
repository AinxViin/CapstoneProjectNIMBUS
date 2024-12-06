package com.example.capstoneproject.ui.plan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.adapter.PlanAdapter
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentAddedPlanBinding
import com.example.capstoneproject.retrofit.ApiConfig
import com.example.capstoneproject.retrofit.ApiService
import kotlinx.coroutines.launch

class AddedPlanFragment : Fragment() {

    private var _binding: FragmentAddedPlanBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PlanAdapter

    // Inisialisasi apiService dan userPreference
    private lateinit var apiService: ApiService
    private lateinit var userPreference: UserPreference

    private val viewModel: AddedPlanViewModel by viewModels {
        // Pass apiService dan userPreference ke UserRepository
        AddedPlanViewModelFactory(UserRepository.getInstance(apiService, userPreference))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Pastikan konteks sudah ter-attach sebelum melakukan inisialisasi
        apiService = ApiConfig.apiService(UserPreference.getInstance(requireContext().dataStore))
        userPreference = UserPreference.getInstance(requireContext().dataStore)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddedPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observePlans()
    }

    private fun setupRecyclerView() {
        adapter = PlanAdapter()
        binding.rvPlan.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlan.adapter = adapter
    }

    private fun observePlans() {
        lifecycleScope.launch {
            val userId = viewModel.getUserId() // Mendapatkan ID pengguna
            viewModel.getPlans(userId).observe(viewLifecycleOwner) { plans ->
                adapter.submitList(plans)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

