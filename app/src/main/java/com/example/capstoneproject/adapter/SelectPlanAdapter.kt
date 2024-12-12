package com.example.capstoneproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.databinding.ItemSelectPlanBinding
import com.example.capstoneproject.response.PlanResponse

class SelectPlanAdapter : RecyclerView.Adapter<SelectPlanAdapter.SelectPlanViewHolder>() {

    private val plans = mutableListOf<PlanResponse>()
    private var onItemClickListener: ((PlanResponse) -> Unit)? = null

    fun setOnItemClickListener(listener: (PlanResponse) -> Unit) {
        onItemClickListener = listener
    }

    fun submitList(newPlans: List<PlanResponse>) {
        plans.clear()
        plans.addAll(newPlans)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectPlanViewHolder {
        val binding =
            ItemSelectPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectPlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectPlanViewHolder, position: Int) {
        holder.bind(plans[position], onItemClickListener)
    }

    override fun getItemCount(): Int = plans.size

    class SelectPlanViewHolder(private val binding: ItemSelectPlanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            plan: PlanResponse,
            onItemClickListener: ((PlanResponse) -> Unit)?
        ) {
            binding.tvPlanTitle.text = plan.nama

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(plan)
            }
        }
    }
}