package com.example.capstoneproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.adapter.ProvinceAdapter
import com.example.capstoneproject.adapter.WisataAdapter
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.di.Injection

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var provinceAdapter: ProvinceAdapter
    private lateinit var alamAdapter: WisataAdapter
    private lateinit var budayaAdapter: WisataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupViewModel()
        setupRecyclerView()
        observeViewModel()
        setupClickListeners()

        return root
    }

    private fun setupViewModel() {
        val factory = Injection.provideViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        viewModel.getProvinces()
        viewModel.getWisata() // Panggil fungsi untuk mendapatkan data wisata
    }

    private fun setupRecyclerView() {
        // RecyclerView untuk provinsi
        provinceAdapter = ProvinceAdapter()
        binding.recyclerViewProvinces.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = provinceAdapter
        }

        // RecyclerView untuk wisata alam
        alamAdapter = WisataAdapter()
        binding.recyclerViewAlam.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = alamAdapter
        }

        // RecyclerView untuk seni dan budaya
        budayaAdapter = WisataAdapter()
        binding.recyclerViewBudaya.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = budayaAdapter
        }
    }

    private fun observeViewModel() {
        // Observasi data provinsi
        viewModel.provinces.observe(viewLifecycleOwner) { provinces ->
            provinceAdapter.submitList(provinces)
        }

        // Observasi data wisata alam
        viewModel.wisataAlam.observe(viewLifecycleOwner) { wisataAlam ->
            alamAdapter.submitList(wisataAlam)
        }

        // Observasi data seni dan budaya
        viewModel.wisataBudaya.observe(viewLifecycleOwner) { wisataBudaya ->
            budayaAdapter.submitList(wisataBudaya)
        }

        // Observasi loading
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observasi pesan error
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.tvSeeAllAlam.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllAlamFragment())
        }

        binding.tvSeeAllBudaya.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllSeniFragment())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
