package com.example.capstoneproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.R
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.adapter.SelectPlanAdapter
import com.example.capstoneproject.data.ItemOffsetDecoration
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentSelectPlanBinding
import com.example.capstoneproject.request.WisataToPlanRequest
import com.example.capstoneproject.response.PlanResponse
import com.example.capstoneproject.retrofit.ApiConfig
import kotlinx.coroutines.launch

class SelectPlanFragment : Fragment() {

    private var wisataId: Int? = null
    private var _binding: FragmentSelectPlanBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SelectPlanAdapter
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        saveInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: SelectPlanFragmentArgs by navArgs()
        wisataId = args.wisataId

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val itemDecoration =
            ItemOffsetDecoration(resources.getDimensionPixelSize(R.dimen.recycler_view_spacing))
        binding.rvSelectPlans.addItemDecoration(itemDecoration)

        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        val apiService = ApiConfig.apiService(userPreference)
        userRepository = UserRepository.getInstance(apiService, userPreference)

        adapter = SelectPlanAdapter()
        binding.rvSelectPlans.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSelectPlans.adapter = adapter

        adapter.setOnItemClickListener { plan ->
            wisataId?.let { wisataId ->
                sendWisatatoPlan(plan.id, wisataId)
            }
        }

        fetchPlans()
    }

    private fun fetchPlans() {
        lifecycleScope.launch {
            try {
                val apiService =
                    ApiConfig.apiService(UserPreference.getInstance(requireContext().dataStore))
                val plans = apiService.getPlans()
                showPlans(plans)
            } catch (e: Exception) {
                Log.e("SelectPlanFragment,", "Error Fetching plans: ${e.message}")
            }
        }
    }

    private fun sendWisatatoPlan(planId: Int, wisataId: Int) {
        val request = WisataToPlanRequest(
            perencanaanManual_id = planId,
            tempatWisata_id = wisataId
        )

        lifecycleScope.launch {
            try {
                val response = userRepository.addWisataToPlan(request)
                if (response) {
                    findNavController().navigate(R.id.action_selectPlanFragment_to_homeFragment)
                    Toast.makeText(
                        requireContext(),
                        "Wisata berhasil ditambahkan",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to add Wisata to Plan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPlans(plans: List<PlanResponse>) {
        adapter.submitList(plans)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
