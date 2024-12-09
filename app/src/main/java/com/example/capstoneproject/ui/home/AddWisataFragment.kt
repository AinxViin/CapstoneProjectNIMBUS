package com.example.capstoneproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.PlanAdapter
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentAddWisataBinding
import com.example.capstoneproject.response.PlanResponse
import com.example.capstoneproject.response.WisataResponse
import com.example.capstoneproject.retrofit.ApiConfig
import com.example.capstoneproject.ui.plan.AddedPlanViewModel
import com.example.capstoneproject.ui.plan.AddedPlanViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class AddWisataFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddWisataBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PlanAdapter
    private lateinit var viewModel: AddedPlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddWisataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi adapter dan RecyclerView
        adapter = PlanAdapter()
        binding.rvPlans.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlans.adapter = adapter

        // Inisialisasi ViewModel
        val userRepository = UserRepository.getInstance(
            ApiConfig.apiService(UserPreference.getInstance(requireContext().dataStore)),
            UserPreference.getInstance(requireContext().dataStore)
        )
        val viewModelFactory = AddedPlanViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddedPlanViewModel::class.java)

        // Panggil fungsi untuk mengambil data rencana
        fetchPlans()
    }

    private fun fetchPlans() {
        lifecycleScope.launch {
            try {
                // Mendapatkan userId dari session
                val userId = viewModel.getUserId()
                viewModel.getPlans(userId).observe(viewLifecycleOwner) { plans ->
                    showPlans(plans)
                }
            } catch (e: Exception) {
                Log.e("AddWisataFragment", "Error fetching plans: ${e.message}")
            }
        }
    }

    private fun showPlans(plans: List<PlanResponse>) {
        // Menampilkan data rencana pada RecyclerView
        adapter.submitList(plans)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "AddWisataFragment"  // Tambahkan konstanta TAG

        // Menambahkan metode newInstance() untuk membuat fragment dengan data wisata
        fun newInstance(wisata: WisataResponse): AddWisataFragment {
            val fragment = AddWisataFragment()
            val bundle = Bundle()
            bundle.putParcelable("WISATA_KEY", wisata)  // Menyimpan data wisata dalam Bundle
            fragment.arguments = bundle
            return fragment
        }
    }
}


