package com.example.capstoneproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var hiburanAdapter: WisataAdapter

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
        setupSeeAllClickListeners()

        return root
    }

    private fun setupViewModel() {
        val factory = Injection.provideViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        viewModel.getProvinces()
        viewModel.getWisata()
    }

    private fun setupSeeAllClickListeners() {
        binding.tvSeeAllAlam.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAllAlamFragment()
            findNavController().navigate(action)
        }

        binding.tvSeeAllBudaya.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAllSeniFragment()
            findNavController().navigate(action)
        }

        binding.tvSeeAllHiburan.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAllHiburanFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        // RecyclerView untuk provinsi
        provinceAdapter = ProvinceAdapter()
        binding.recyclerViewProvinces.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = provinceAdapter
        }

        // RecyclerView untuk wisata alam
        alamAdapter = WisataAdapter { wisataId ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailWisataFragment(wisataId)
            findNavController().navigate(action)
        }
        binding.recyclerViewAlam.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = alamAdapter
        }

        // RecyclerView untuk seni dan budaya
        budayaAdapter = WisataAdapter { wisataId ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailWisataFragment(wisataId)
            findNavController().navigate(action)
        }
        binding.recyclerViewBudaya.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = budayaAdapter
        }

        // RecyclerView untuk hiburan
        hiburanAdapter = WisataAdapter { wisataId ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailWisataFragment(wisataId)
            findNavController().navigate(action)
        }
        binding.recyclerViewHiburan.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = hiburanAdapter
        }
    }

    private fun observeViewModel() {
        // Observasi data provinsi
        viewModel.provinces.observe(viewLifecycleOwner) { provinces ->
            provinceAdapter.submitList(provinces)
        }

        // Observasi data wisata alam
        viewModel.wisataAlam.observe(viewLifecycleOwner) { wisataAlam ->
            val limitedAlam = wisataAlam.take(6)
            alamAdapter.submitList(limitedAlam)
        }

        // Observasi data seni dan budaya
        viewModel.wisataBudaya.observe(viewLifecycleOwner) { wisataBudaya ->
            val limitedBudaya = wisataBudaya.take(6)
            budayaAdapter.submitList(limitedBudaya)
        }

        // Observasi data wisata hiburan
        viewModel.wisataHiburan.observe(viewLifecycleOwner) { wisataHiburan ->
            val limitedHiburan = wisataHiburan.take(6)
            hiburanAdapter.submitList(limitedHiburan)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
