package com.example.capstoneproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
import com.example.capstoneproject.response.WisataResponse

class WisataAdapter(private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<WisataAdapter.WisataViewHolder>() {

    private val wisataList = mutableListOf<WisataResponse>()

    fun submitList(list: List<WisataResponse>) {
        wisataList.clear()
        wisataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return WisataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        holder.bind(wisataList[position], onItemClick)
    }

    override fun getItemCount(): Int = wisataList.size

    class WisataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvCategoryTitle)
        private val thumbnail: ImageView = itemView.findViewById(R.id.ivCategoryImage)

        fun bind(wisata: WisataResponse, onItemClick: (Int) -> Unit) {
            title.text = wisata.nama

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
