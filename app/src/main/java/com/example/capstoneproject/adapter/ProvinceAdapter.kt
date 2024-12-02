package com.example.capstoneproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
import com.example.capstoneproject.response.ProvinceResponse

class ProvinceAdapter : RecyclerView.Adapter<ProvinceAdapter.ProvinceViewHolder>() {

    private val provinceList = mutableListOf<ProvinceResponse>()

    fun submitList(list: List<ProvinceResponse>) {
        provinceList.clear()
        provinceList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_province, parent, false)
        return ProvinceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProvinceViewHolder, position: Int) {
        holder.bind(provinceList[position])
    }

    override fun getItemCount(): Int = provinceList.size

    class ProvinceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvProvinceName)
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.ivProvinceThumbnail)

        fun bind(province: ProvinceResponse) {
            nameTextView.text = province.nama
            Glide.with(itemView.context)
                .load(province.thumbnail)
                .into(thumbnailImageView)
        }
    }
}