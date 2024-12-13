package com.example.capstoneproject.ui.plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.adapter.DestinationAdapter
import com.example.capstoneproject.databinding.FragmentDetailPlanBinding
import com.example.capstoneproject.di.Injection
import kotlinx.coroutines.launch

class DetailPlanFragment : Fragment() {
    private var _binding: FragmentDetailPlanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailPlanViewModel by viewModels {
        DetailPlanViewModelFactory(Injection.provideRepository(requireContext()))
    }
    private lateinit var adapter: DestinationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DestinationAdapter()
        binding.rvDestinations.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDestinations.adapter = adapter

        val planId = arguments?.getInt("planId") ?: return

        adapter.setOnDeleteClickListener { destination ->
            lifecycleScope.launch {
                try {
                    val success = viewModel.deleteDestination(planId, destination.id)
                    if (success) {
                        adapter.submitList(adapter.destinations.filter { it.id != destination.id })
                    }
                } catch (e: Exception) {
                    Log.e("DetailPlanFragment", "Error deleting destination: ${e.message}")
                }
            }
        }

        fetchDestinations(planId)
    }

    private fun fetchDestinations(planId: Int) {
        lifecycleScope.launch {
            viewModel.getDestinations(planId).collect { planDestinations ->
                val destinationDetails = planDestinations.flatMap { it.tw_perencanaan_manual }

                if (destinationDetails.isEmpty()) {
                    binding.tvEmptyMessage.visibility = View.VISIBLE
                    binding.rvDestinations.visibility = View.GONE
                } else {
                    binding.tvEmptyMessage.visibility = View.GONE
                    binding.rvDestinations.visibility = View.VISIBLE
                    adapter.submitList(destinationDetails)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
