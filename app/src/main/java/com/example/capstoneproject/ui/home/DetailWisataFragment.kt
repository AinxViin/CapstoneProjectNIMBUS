package com.example.capstoneproject.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.datastore.preferences.preferencesDataStore
import com.example.capstoneproject.R
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.databinding.FragmentDetailWisataBinding

val Context.dataStore by preferencesDataStore(name = "session")

class DetailWisataFragment : Fragment() {

    private lateinit var binding: FragmentDetailWisataBinding
    private lateinit var viewModel: DetailWisataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailWisataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil parameter wisataId dari argumen fragment
        val wisataId = arguments?.getInt("wisataId") ?: return

        // Mengambil UserPreference menggunakan DataStore dari Context
        val userPreference = UserPreference.getInstance(requireContext().dataStore)

        // Membuat factory untuk ViewModel
        val factory = DetailWisataViewModelFactory(userPreference)

        // Menggunakan factory untuk mendapatkan ViewModel
        viewModel = ViewModelProvider(this, factory).get(DetailWisataViewModel::class.java)

        // Memanggil ViewModel untuk mengambil data wisata berdasarkan ID
        viewModel.fetchDetailWisata(wisataId)

        binding.btnAddToPlan.setOnClickListener {
            val action = DetailWisataFragmentDirections.actionDetailWisataFragmentToSelectPlanFragment(wisataId)
            findNavController().navigate(action)
        }


        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.detailWisata.observe(viewLifecycleOwner) { detail ->
            binding.namaWisata.text = detail.nama
            binding.provinsi.text = detail.provinsi.nama
            binding.kategoriWisata.text = detail.categoryWisata.nama
            binding.reviewTotal.text = detail.review_total
            binding.website.text = detail.website
            binding.googleLink.text = detail.google_link
            binding.latitude.text = detail.latitude
            binding.longitude.text = detail.longitude

            Glide.with(requireContext())
                .load(detail.thumbnail)
                .into(binding.thumbnail)
        }
    }
}
