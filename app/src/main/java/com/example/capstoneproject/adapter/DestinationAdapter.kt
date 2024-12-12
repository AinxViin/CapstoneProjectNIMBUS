package com.example.capstoneproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.data.DestinationDetail
import com.example.capstoneproject.databinding.ItemDestinationBinding

class DestinationAdapter : RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>() {
    val destinations = mutableListOf<DestinationDetail>()
    private var onDeleteClickListener: ((DestinationDetail) -> Unit)? = null

    fun submitList(newDestinations: List<DestinationDetail>) {
        destinations.clear()
        destinations.addAll(newDestinations)
        notifyDataSetChanged()
    }

    fun setOnDeleteClickListener(listener: (DestinationDetail) -> Unit) {
        onDeleteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val binding = ItemDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        holder.bind(destinations[position], onDeleteClickListener)
    }

    override fun getItemCount(): Int = destinations.size

    class DestinationViewHolder(private val binding: ItemDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            destination: DestinationDetail,
            onDeleteClickListener: ((DestinationDetail) -> Unit)?
        ) {
            // Set text
            binding.tvDestinationName.text = destination.tempatWisata.nama
            binding.tvDestinationAddress.text = destination.tempatWisata.alamat

            // Load image using Glide
            Glide.with(binding.ivDestinationImage.context)
                .load(destination.tempatWisata.thumbnail)
                .into(binding.ivDestinationImage)

            // Set delete button listener
            binding.btnDelete.setOnClickListener {
                onDeleteClickListener?.invoke(destination)
            }
        }
    }
}
