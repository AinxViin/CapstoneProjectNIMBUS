package com.example.capstoneproject.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentChangeEmailBinding
import com.example.capstoneproject.request.UpdateRequest
import com.example.capstoneproject.retrofit.ApiConfig
import kotlinx.coroutines.launch

class ChangeEmailFragment : Fragment() {

    private var _binding: FragmentChangeEmailBinding? = null
    private val binding get() = _binding!!

    private lateinit var userPreference: UserPreference
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeEmailBinding.inflate(inflater, container, false)
        userPreference = UserPreference.getInstance(requireContext().dataStore)

        setupUI()
        return binding.root
    }

    private fun setupUI() {
        lifecycleScope.launch {
            userPreference.getSession().collect { user ->
                binding.oldEmailEditText.setText(user.email)
            }
        }

        binding.saveEmailButton.setOnClickListener {
            val newEmail = binding.newEmailEditText.text.toString().trim()
            if (newEmail.isEmpty()) {
                Toast.makeText(requireContext(), "Email baru tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    profileViewModel.updateInfo(
                        updateRequest = UpdateRequest(
                            email = newEmail
                        ),
                        onResult = { status, message ->
                            if (status) {
                                lifecycleScope.launch {
                                    userPreference.updateEmail(newEmail)
                                }
                                Toast.makeText(requireContext(), "Email berhasil diperbarui", Toast.LENGTH_SHORT).show()
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
