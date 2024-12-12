package com.example.capstoneproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.WisataHiburanAdapter
import com.example.capstoneproject.adapter.WisataSeniAdapter
import com.example.capstoneproject.data.ItemOffsetDecoration
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentAllHiburanBinding
import com.example.capstoneproject.databinding.FragmentAllSeniBinding
import com.example.capstoneproject.retrofit.ApiConfig
import kotlinx.coroutines.launch

class AllSeniFragment : Fragment() {

    private var _binding: FragmentAllSeniBinding? = null
    private val binding get() = _binding!!
    private lateinit var wisataSeniAdapter: WisataSeniAdapter

    private val userRepository by lazy {
        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        UserRepository.getInstance(ApiConfig.apiService(userPreference), userPreference)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllSeniBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchWisataSeni()
    }

    private fun setupRecyclerView() {
        val itemDecoration = ItemOffsetDecoration(resources.getDimensionPixelSize(R.dimen.recycler_view_spacing))
        binding.rvAllCategories.addItemDecoration(itemDecoration)
        binding.rvAllCategories.layoutManager = LinearLayoutManager(requireContext())
        wisataSeniAdapter = WisataSeniAdapter(emptyList())
        binding.rvAllCategories.adapter = wisataSeniAdapter

        wisataSeniAdapter.setOnItemClickListener { wisata ->
            val wisataId = wisata.id

            val action = AllSeniFragmentDirections.actionAllSeniFragmentToDetailWisataFragment(wisataId)
            findNavController().navigate(action)
        }
    }

    private fun fetchWisataSeni() {
        lifecycleScope.launch {
            try {
                val wisataSeni = userRepository.getWisataSeni()
                wisataSeniAdapter = WisataSeniAdapter(wisataSeni)
                binding.rvAllCategories.adapter = wisataSeniAdapter
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
