package com.example.capstoneproject.ui.plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.PlanAdapter
import com.example.capstoneproject.data.ItemOffsetDecoration
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentAddedPlanBinding
import com.example.capstoneproject.retrofit.ApiConfig
import com.example.capstoneproject.response.PlanResponse
import kotlinx.coroutines.launch
import retrofit2.Response

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

        adapter = PlanAdapter()
        binding.rvPlan.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlan.adapter = adapter

        val itemDecoration = ItemOffsetDecoration(resources.getDimensionPixelSize(R.dimen.recycler_view_spacing))
        binding.rvPlan.addItemDecoration(itemDecoration)

        adapter.setOnItemClickListener { plan ->
            val action =
                AddedPlanFragmentDirections.actionAddedPlanFragmentToDetailPlanFragment(plan.id)
            findNavController().navigate(action)
        }

        // Tambahkan listener untuk Button Tambah Rencana
        binding.btnAddPlan.setOnClickListener {
            val bottomSheet = AddPlanFragment()
            bottomSheet.show(parentFragmentManager, "AddPlanBottomSheet")
        }

        // Tambahkan listener untuk tombol delete
        adapter.setOnItemDeleteListener { planId ->
            deletePlan(planId)
        }

        fetchPlans() // Panggil fungsi untuk mengambil data rencana
    }

    private fun fetchPlans() {
        // Panggil API untuk mendapatkan daftar rencana
        lifecycleScope.launch {
            try {
                val apiService =
                    ApiConfig.apiService(UserPreference.getInstance(requireContext().dataStore))
                val plans = apiService.getPlans() // Ambil data rencana
                showPlans(plans)
            } catch (e: Exception) {
                Log.e("AddedPlanFragment", "Error fetching plans: ${e.message}")
            }
        }
    }

    private fun showPlans(plans: List<PlanResponse>) {
        if (plans.isEmpty()) {
            findNavController().navigate(R.id.action_addedPlanFragment_to_planFragment)
        } else {
            adapter.submitList(plans)
        }
    }

    private fun deletePlan(planId: Int) {
        lifecycleScope.launch {
            try {
                val apiService =
                    ApiConfig.apiService(UserPreference.getInstance(requireContext().dataStore))
                val response: Response<Unit> = apiService.deletePlan(planId)

                if (response.isSuccessful) {
                    val updatedPlans =
                        adapter.getPlans().filterNot { it.id == planId } // Gunakan getPlans()
                    adapter.submitList(updatedPlans) // Memperbarui list
                    Log.d("AddedPlanFragment", "Plan berhasil dihapus")
                } else {
                    Log.e("AddedPlanFragment", "Gagal menghapus plan")
                }
            } catch (e: Exception) {
                Log.e("AddedPlanFragment", "Error deleting plan: ${e.message}")
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
