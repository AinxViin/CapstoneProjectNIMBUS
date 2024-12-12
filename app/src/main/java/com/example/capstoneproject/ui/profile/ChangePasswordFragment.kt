package com.example.capstoneproject.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentChangePasswordBinding
import com.example.capstoneproject.request.UpdateRequest
import kotlinx.coroutines.launch

class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
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
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        userPreference = UserPreference.getInstance(requireContext().dataStore)

        binding.savePasswordButton.setOnClickListener {
            val oldPassword = binding.oldPasswordEditText.text.toString().trim()
            val newPassword = binding.newPasswordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            if (newPassword != confirmPassword) {
                Toast.makeText(requireContext(), "Password tidak cocok", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    userPreference.getSession().collect { user ->
                        if (oldPassword != user.password) {
                            Toast.makeText(
                                requireContext(),
                                "Kata sandi lama salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            profileViewModel.updateInfo(
                                updateRequest = UpdateRequest(
                                    password = newPassword
                                ),
                                onResult = { status, message ->
                                    if (status) {
                                        lifecycleScope.launch {
                                            userPreference.updatePassword(newPassword)
                                        }
                                        Toast.makeText(
                                            requireContext(),
                                            "Password berhasil diperbarui",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        parentFragmentManager.popBackStack()
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
