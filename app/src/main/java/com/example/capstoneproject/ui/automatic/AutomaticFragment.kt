package com.example.capstoneproject.ui.automatic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.databinding.FragmentAutomaticBinding

class AutomaticFragment : Fragment() {

    private var _binding: FragmentAutomaticBinding? = null
    private val binding get() = _binding!!

    private lateinit var automaticViewModel: AutomaticViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutomaticBinding.inflate(inflater, container, false)
        val root: View = binding.root

        automaticViewModel = ViewModelProvider(this).get(AutomaticViewModel::class.java)

        val textView: TextView = binding.textAutomatic
        automaticViewModel.text.observe(viewLifecycleOwner) { text ->
            textView.text = text
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
