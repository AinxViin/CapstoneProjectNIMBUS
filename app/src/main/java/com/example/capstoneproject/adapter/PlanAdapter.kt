package com.example.capstoneproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ItemPlanBinding
import com.example.capstoneproject.response.PlanResponse

class PlanAdapter : RecyclerView.Adapter<PlanAdapter.PlanViewHolder>() {

    private val plans = mutableListOf<PlanResponse>()

    // Fungsi untuk memperbarui data dalam adapter
    fun submitList(newPlans: List<PlanResponse>) {
        plans.clear()
        plans.addAll(newPlans)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val binding = ItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        holder.bind(plans[position])
    }

    override fun getItemCount(): Int = plans.size

    // ViewHolder untuk setiap item dalam RecyclerView
    class PlanViewHolder(private val binding: ItemPlanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plan: PlanResponse) {
            // Menampilkan nama rencana di TextView
            binding.tvPlanTitle.text = plan.nama

        }
    }
}
