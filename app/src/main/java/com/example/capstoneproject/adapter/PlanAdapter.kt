package com.example.capstoneproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.databinding.ItemPlanBinding
import com.example.capstoneproject.response.PlanResponse

class PlanAdapter : RecyclerView.Adapter<PlanAdapter.PlanViewHolder>() {

    private val plans = mutableListOf<PlanResponse>()
    private var onItemClickListener: ((PlanResponse) -> Unit)? = null
    private var onItemDeleteListener: ((Int) -> Unit)? = null  // Tambahkan listener untuk delete

    // Fungsi untuk mengirimkan daftar baru
    fun submitList(newPlans: List<PlanResponse>) {
        plans.clear()
        plans.addAll(newPlans)
        notifyDataSetChanged()  // Memperbarui daftar
    }

    // Fungsi untuk mengatur listener item klik
    fun setOnItemClickListener(listener: (PlanResponse) -> Unit) {
        onItemClickListener = listener
    }

    // Fungsi untuk mengatur listener item delete
    fun setOnItemDeleteListener(listener: (Int) -> Unit) {
        onItemDeleteListener = listener
    }

    // Fungsi untuk mengambil data rencana
    fun getPlans(): List<PlanResponse> = plans

    // Mengembalikan ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val binding = ItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanViewHolder(binding)
    }

    // Menyambungkan data rencana ke tampilan (view holder)
    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        holder.bind(plans[position], onItemClickListener, onItemDeleteListener)
    }

    // Mengembalikan jumlah item di RecyclerView
    override fun getItemCount(): Int = plans.size

    // ViewHolder yang menangani binding data ke item
    class PlanViewHolder(private val binding: ItemPlanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            plan: PlanResponse,
            onItemClickListener: ((PlanResponse) -> Unit)?,
            onItemDeleteListener: ((Int) -> Unit)?
        ) {
            binding.tvPlanTitle.text = plan.nama  // Menampilkan nama rencana

            // Set listener untuk klik item
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(plan)
            }

            // Set listener untuk tombol delete
            binding.btnDeletePlan.setOnClickListener {
                onItemDeleteListener?.invoke(plan.id)  // Panggil listener dengan id plan
            }
        }
    }
}
