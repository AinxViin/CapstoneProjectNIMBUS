package com.example.capstoneproject.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.R
import com.example.capstoneproject.di.Injection
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class AddPlanFragment : BottomSheetDialogFragment() {

    private lateinit var etPlanName: EditText
    private lateinit var btnCreatePlan: Button

    // Initialize ViewModel with a Factory
    private val addPlanViewModel: AddPlanViewModel by viewModels {
        AddPlanViewModelFactory(Injection.provideRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etPlanName = view.findViewById(R.id.etPlanName)
        btnCreatePlan = view.findViewById(R.id.btnCreatePlan)

        btnCreatePlan.setOnClickListener {
            val planName = etPlanName.text.toString()
            if (planName.isNotBlank()) {
                lifecycleScope.launch {
                    try {
                        addPlanViewModel.addPlan(planName)
                        Toast.makeText(
                            requireContext(),
                            "Rencana dibuat: $planName",
                            Toast.LENGTH_SHORT
                        ).show()
                        dismiss()
                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Gagal membuat rencana: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Nama rencana tidak boleh kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
