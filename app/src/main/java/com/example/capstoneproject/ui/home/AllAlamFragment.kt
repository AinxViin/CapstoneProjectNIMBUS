package com.example.capstoneproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.WisataAlamAdapter
import com.example.capstoneproject.data.ItemOffsetDecoration
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentAllAlamBinding
import com.example.capstoneproject.retrofit.ApiConfig
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController

class AllAlamFragment : Fragment() {

    private var _binding: FragmentAllAlamBinding? = null
    private val binding get() = _binding!!
    private lateinit var wisataAlamAdapter: WisataAlamAdapter

    private val userRepository by lazy {
        val userPreference = UserPreference.getInstance(requireContext().dataStore)

        UserRepository.getInstance(ApiConfig.apiService(userPreference), userPreference)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllAlamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchWisataAlam()
    }

    private fun setupRecyclerView() {
        val itemDecoration = ItemOffsetDecoration(resources.getDimensionPixelSize(R.dimen.recycler_view_spacing))
        binding.rvAllCategories.addItemDecoration(itemDecoration)
        binding.rvAllCategories.layoutManager = LinearLayoutManager(requireContext())
        wisataAlamAdapter = WisataAlamAdapter(emptyList()) // Initial empty list
        binding.rvAllCategories.adapter = wisataAlamAdapter

        // Set listener for item click
        wisataAlamAdapter.setOnItemClickListener { wisata ->
            // Mengambil ID wisata yang dipilih
            val wisataId = wisata.id

            // Menggunakan NavController untuk berpindah ke DetailWisataFragment dan mengirimkan ID
            val action = AllAlamFragmentDirections.actionAllAlamFragmentToDetailWisataFragment(wisataId)
            findNavController().navigate(action)
        }
    }

    private fun fetchWisataAlam() {
        // Fetch data from API
        lifecycleScope.launch {
            try {
                // Menggunakan method suspend yang sudah ada di UserRepository
                val wisataAlam = userRepository.getWisataAlam()
                wisataAlamAdapter = WisataAlamAdapter(wisataAlam)
                binding.rvAllCategories.adapter = wisataAlamAdapter
            } catch (e: Exception) {
                // Handle error, misalnya dengan menampilkan Toast
                e.printStackTrace() // Log error
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
