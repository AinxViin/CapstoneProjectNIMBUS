package com.example.capstoneproject.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.data.pref.dataStore
import com.example.capstoneproject.databinding.FragmentProfileBinding
import com.example.capstoneproject.request.UpdateRequest
import com.example.capstoneproject.ui.login.LoginActivity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userPreference: UserPreference
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            binding.profileImageView.setImageURI(uri)

            profileViewModel.updateInfo(
                updateRequest = UpdateRequest(
                    profilePic = uri.toString()
                ),
                onResult = { status, message ->
                    if (status) {
                        lifecycleScope.launch {
                            try {
                                userPreference.updateProfile(uri.toString())
                                Toast.makeText(requireContext(), "Profile picture updated successfully", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(requireContext(), "Failed to save profile picture: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Failed to update profile: $message", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        } else {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        userPreference = UserPreference.getInstance(requireContext().dataStore)

        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Set dummy data atau load data dari preference/user session
        lifecycleScope.launch {
            userPreference.getSession().collect { user ->
                binding.nameTextView.text = user.name
                binding.emailTextView.text = user.email
                binding.tvName.text = user.name
                binding.tvEmail.text = user.email
                binding.tvPassword.text = "*".repeat(user.password.length)
                val profileUrl = user.profile

                Glide.with(requireContext())
                    .load(profileUrl)
                    .placeholder(binding.profileImageView.drawable)
                    .error(R.drawable.ic_profile_placeholder)
                    .into(binding.profileImageView)
            }
        }

        binding.profileImageView.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Navigasi ke halaman ganti nama
        binding.changeNameButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_change_name)
        }

        // Navigasi ke halaman ganti email
        binding.changeEmailButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_change_email)
        }

        // Navigasi ke halaman ganti password
        binding.changePasswordButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_change_password)
        }

        // Logout
        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            userPreference.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
