package com.example.capstoneproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
import com.example.capstoneproject.response.WisataCategoryResponseItem

class WisataCategoryAdapter : RecyclerView.Adapter<WisataCategoryAdapter.ProvinceViewHolder>() {

    val categoryList = mutableListOf<WisataCategoryResponseItem>()

    fun submitList(list: List<WisataCategoryResponseItem>) {
        Log.d("Adapter", "List received: $list")
        categoryList.clear()
        categoryList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_province, parent, false)
        return ProvinceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProvinceViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size

    class ProvinceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvProvinceName)
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.ivProvinceThumbnail)

        fun bind(category: WisataCategoryResponseItem) {
            nameTextView.text = category.nama
            Glide.with(itemView.context)
                .load(category.thumbnail)
                .placeholder(R.drawable.placeholder)
                .into(thumbnailImageView)
        }
    }
}