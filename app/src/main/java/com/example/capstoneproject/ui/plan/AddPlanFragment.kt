package com.example.capstoneproject.ui.plan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.capstoneproject.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddPlanFragment : BottomSheetDialogFragment() {
    private lateinit var etPlanName: EditText
    private lateinit var btnCreatePlan: Button

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
                Toast.makeText(requireContext(), "Rencana dibuat: $planName", Toast.LENGTH_SHORT).show()
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Nama rencana tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}