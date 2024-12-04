package com.example.capstoneproject.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.capstoneproject.R

class PlanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnManual: Button = view.findViewById(R.id.btnManual)

        btnManual.setOnClickListener {
            val bottomSheet = AddPlanFragment()
            bottomSheet.show(parentFragmentManager, "PlanBottomSheetFragment")
        }
    }
}
