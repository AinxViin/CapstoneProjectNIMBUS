package com.example.capstoneproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
import com.example.capstoneproject.response.WisataResponse

class RekomendasiAdapter(private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<RekomendasiAdapter.WisataViewHolder>() {

    private val wisataList = mutableListOf<WisataResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<WisataResponse>) {
        wisataList.clear()
        wisataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rekomendasi_list, parent, false)
        return WisataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        holder.bind(wisataList[position], onItemClick)
    }

    override fun getItemCount(): Int = wisataList.size

    class WisataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnail: ImageView = itemView.findViewById(R.id.ivCategoryImage)

        fun bind(wisata: WisataResponse, onItemClick: (Int) -> Unit) {
            Glide.with(itemView.context)
                .load(wisata.thumbnail) // Gambar utama dari data wisata
                .placeholder(R.drawable.placeholder) // Placeholder saat gambar belum dimuat
                .into(thumbnail)

            itemView.setOnClickListener {
                // Panggil onItemClick dengan ID wisata saat item diklik
                onItemClick(wisata.id)
            }
        }
    }
}
