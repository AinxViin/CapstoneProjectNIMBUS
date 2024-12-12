package com.example.capstoneproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
import com.example.capstoneproject.data.WisataDetail
import com.example.capstoneproject.databinding.ItemAllwisataBinding

class WisataSeniAdapter (private var wisataList: List<WisataDetail>) : RecyclerView.Adapter<WisataSeniAdapter.WisataSeniViewHolder>() {

    private var onItemClickListener: ((WisataDetail) -> Unit)? = null

    fun setOnItemClickListener(listener: (WisataDetail) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataSeniViewHolder {
        val binding = ItemAllwisataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WisataSeniViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WisataSeniViewHolder, position: Int) {
        val wisata = wisataList[position]
        holder.bind(wisata)
    }

    override fun getItemCount(): Int = wisataList.size

    inner class WisataSeniViewHolder(private val binding: ItemAllwisataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wisata: WisataDetail) {
            binding.tvWisataTitle.text = wisata.nama

            Glide.with(binding.root.context)
                .load(wisata.thumbnail)
                .placeholder(R.drawable.placeholder)
                .into(binding.ivWisataImage)

            binding.root.setOnClickListener {
                println("Item clicked: ${wisata.nama}")
                onItemClickListener?.invoke(wisata)
            }
        }
    }
}