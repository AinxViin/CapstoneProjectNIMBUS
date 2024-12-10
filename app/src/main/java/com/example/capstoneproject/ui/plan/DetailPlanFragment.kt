/*package com.example.capstoneproject.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.adapter.WisataAdapter
import com.example.capstoneproject.databinding.FragmentDetailPlanBinding
import com.example.capstoneproject.response.PlanResponse
import com.example.capstoneproject.response.WisataResponse
import com.example.capstoneproject.retrofit.ApiConfig
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

class DetailPlanFragment : Fragment() {

    private var _binding: FragmentDetailPlanBinding? = null
    private val binding get() = _binding!!

    // Using SafeArgs to receive the selected Plan
    private val args: DetailPlanFragmentArgs by navArgs()

    private lateinit var wisataAdapter: WisataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the selected plan from args
        val plan: PlanResponse = args.selectedPlan

        // Set up UI components
        binding.tvPlanName.text = plan.nama

        // Initialize WisataAdapter
        wisataAdapter = WisataAdapter { wisata ->
            // Handle favorite click if needed (optional)
        }

        // Set up RecyclerView
        binding.rvWisata.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = wisataAdapter
        }

        // Load Wisata data based on categoryWisata_id
        loadWisata(plan.categoryWisata_id)
    }

    private fun loadWisata(categoryWisataId: Int?) {
        // Gantilah dengan pemanggilan API atau pengambilan data wisata berdasarkan kategori
        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.apiService()

                // Pastikan categoryWisataId tidak null dan berikan page yang sesuai
                if (categoryWisataId != null) {
                    val wisataList = apiService.getWisata(page = 1, categoryId = categoryWisataId) // Menggunakan getWisata yang sudah ada
                    wisataAdapter.submitList(wisataList)
                }
            } catch (e: Exception) {
                // Tangani error, misalnya log error
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
*/