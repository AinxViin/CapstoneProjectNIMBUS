package com.example.capstoneproject.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.WisataHiburanAdapter
import com.example.capstoneproject.data.ItemOffsetDecoration
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentAllHiburanBinding
import com.example.capstoneproject.retrofit.ApiConfig
import kotlinx.coroutines.launch

class AllHiburanFragment : Fragment() {

    private var _binding: FragmentAllHiburanBinding? = null
    private val binding get() = _binding!!
    private lateinit var wisataHiburanAdapter: WisataHiburanAdapter

    private val userRepository by lazy {
        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        UserRepository.getInstance(ApiConfig.apiService(userPreference), userPreference)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllHiburanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        setupRecyclerView()
        fetchWisataHiburan()
    }

    private fun setupRecyclerView() {
        val itemDecoration =
            ItemOffsetDecoration(resources.getDimensionPixelSize(R.dimen.recycler_view_spacing))
        binding.rvAllCategories.addItemDecoration(itemDecoration)
        binding.rvAllCategories.layoutManager = LinearLayoutManager(requireContext())
        wisataHiburanAdapter = WisataHiburanAdapter(emptyList())
        binding.rvAllCategories.adapter = wisataHiburanAdapter

        wisataHiburanAdapter.setOnItemClickListener { wisata ->
            val wisataId = wisata.id

            val action =
                AllHiburanFragmentDirections.actionAllHiburanFragmentToDetailWisataFragment(wisataId)
            findNavController().navigate(action)
        }
    }

    private fun fetchWisataHiburan() {
        lifecycleScope.launch {
            try {
                val wisataHiburan = userRepository.getWisataHiburan()
                wisataHiburanAdapter = WisataHiburanAdapter(wisataHiburan)
                binding.rvAllCategories.adapter = wisataHiburanAdapter
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
