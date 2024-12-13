package com.example.capstoneproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.databinding.ItemPlanBinding
import com.example.capstoneproject.response.PlanResponse

class PlanAdapter : RecyclerView.Adapter<PlanAdapter.PlanViewHolder>() {

    private val plans = mutableListOf<PlanResponse>()
    private var onItemClickListener: ((PlanResponse) -> Unit)? = null
    private var onItemDeleteListener: ((Int) -> Unit)? = null

    fun submitList(newPlans: List<PlanResponse>) {
        plans.clear()
        plans.addAll(newPlans)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (PlanResponse) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemDeleteListener(listener: (Int) -> Unit) {
        onItemDeleteListener = listener
    }

    fun getPlans(): List<PlanResponse> = plans

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val binding = ItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        holder.bind(plans[position], onItemClickListener, onItemDeleteListener)
    }

    override fun getItemCount(): Int = plans.size

    class PlanViewHolder(private val binding: ItemPlanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            plan: PlanResponse,
            onItemClickListener: ((PlanResponse) -> Unit)?,
            onItemDeleteListener: ((Int) -> Unit)?
        ) {
            binding.tvPlanTitle.text = plan.nama

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(plan)
            }

            binding.btnDeletePlan.setOnClickListener {
                onItemDeleteListener?.invoke(plan.id)
            }
        }
    }
}
