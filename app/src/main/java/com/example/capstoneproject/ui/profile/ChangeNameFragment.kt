package com.example.capstoneproject.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.ViewModelFactory
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentChangeNameBinding
import com.example.capstoneproject.request.UpdateRequest
import kotlinx.coroutines.launch

class ChangeNameFragment : Fragment() {

    private var _binding: FragmentChangeNameBinding? = null
    private val binding get() = _binding!!

    private lateinit var userPreference: UserPreference
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeNameBinding.inflate(inflater, container, false)
        userPreference = UserPreference.getInstance(requireContext().dataStore)

        setupUI()
        return binding.root
    }

    private fun setupUI() {
        lifecycleScope.launch {
            userPreference.getSession().collect { user ->
                binding.oldNameEditText.setText(user.name)
            }
        }

        binding.saveNameButton.setOnClickListener {
            val newName = binding.newNameEditText.text.toString().trim()
            if (newName.isEmpty()) {
                Toast.makeText(requireContext(), "Nama baru tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else {
                lifecycleScope.launch {
                    profileViewModel.updateInfo(
                        updateRequest = UpdateRequest(
                            nama = newName
                        ),
                        onResult = { status, message ->
                            if (status) {
                                lifecycleScope.launch {
                                    userPreference.updateName(newName)
                                }
                                Toast.makeText(
                                    requireContext(),
                                    "Nama berhasil diperbarui",
                                    Toast.LENGTH_SHORT
                                ).show()
                                parentFragmentManager.popBackStack()
                            } else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
