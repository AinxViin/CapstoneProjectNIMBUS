package com.example.capstoneproject.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.di.Injection

class PlanFragment : Fragment() {

    private val planViewModel: PlanViewModel by activityViewModels {
        PlanViewModelFactory(Injection.provideRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            planViewModel.plans.collect { plans ->
                if (plans.isNotEmpty() && isAdded) {
                    findNavController().navigate(R.id.action_navigation_plan_to_addedPlanFragment)
                }
            }
        }

        planViewModel.fetchPlans()

        val btnManual: Button = view.findViewById(R.id.btnManual)
        btnManual.setOnClickListener {
            val bottomSheet = AddPlanFragment()
            bottomSheet.show(parentFragmentManager, "PlanBottomSheetFragment")
        }
    }
}
