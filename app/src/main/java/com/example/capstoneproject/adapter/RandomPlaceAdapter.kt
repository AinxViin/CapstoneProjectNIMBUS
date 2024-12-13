package com.example.capstoneproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.MainActivity
import com.example.capstoneproject.R
import com.example.capstoneproject.response.RandomPlacesItem

class RandomPlaceAdapter : RecyclerView.Adapter<RandomPlaceAdapter.WisataViewHolder>() {

    private val wisataList = mutableListOf<RandomPlacesItem>()

    fun submitList(list: List<RandomPlacesItem>) {
        wisataList.clear()
        wisataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rekomendasi, parent, false)
        return WisataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WisataViewHolder, position: Int) {
        holder.bind(wisataList[position])
        holder.itemView.setOnClickListener {
            val wisataId = wisataList[position].tempatWisata?.id

            if (wisataId != null) {
                val intent = Intent(holder.itemView.context, MainActivity::class.java)

                intent.putExtra("wisataId", wisataId)

                holder.itemView.context.startActivity(intent)
            }
        }
    }


    override fun getItemCount(): Int = wisataList.size

    class WisataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val namaDestinasi: TextView = itemView.findViewById(R.id.tvNamaDestinasi)
        private val letakProvinsi: TextView = itemView.findViewById(R.id.tvLetakProvinsi)
        private val kategori: TextView = itemView.findViewById(R.id.tvKategori)
        private val llRating: LinearLayout = itemView.findViewById(R.id.llRating)
        private val ivCategoryPicture: ImageView = itemView.findViewById(R.id.ivCategoryImage)

        @SuppressLint("SetTextI18n", "DefaultLocale")
        fun bind(wisata: RandomPlacesItem) {
            llRating.removeAllViews()

            val ratingDouble: Double? = wisata.tempatWisata?.predictedRating
            val ratingInt: Int = ratingDouble?.toInt() ?: 0

            val maxStars = 5
            val displayRating =
                ratingInt.coerceIn(0, maxStars)
            val wisataAlamImage = listOf(
                R.drawable.alam_1,
                R.drawable.alam_2,
                R.drawable.alam_3,
                R.drawable.alam_4,
                R.drawable.alam_5
            )
            val hiburanImage = listOf(
                R.drawable.hiburan_1,
                R.drawable.hiburan_2,
                R.drawable.hiburan_3,
                R.drawable.hiburan_4,
                R.drawable.hiburan_5
            )
            val seniBudayaImage = listOf(
                R.drawable.senbud_1,
                R.drawable.senbud_2,
                R.drawable.senbud_3,
                R.drawable.senbud_4,
                R.drawable.senbud_5
            )

            val randomAlamImage = wisataAlamImage.random()
            val randomHiburanImage = hiburanImage.random()
            val randomSeniBudayaImage = seniBudayaImage.random()

            val category = wisata.tempatWisata?.kategori

            namaDestinasi.text = wisata.tempatWisata?.namaDestinasi ?: "Unknown"
            letakProvinsi.text = "Provinsi : ${wisata.tempatWisata?.letakProvinsi ?: "Unknown"}"
            kategori.text = "Kategori : ${wisata.tempatWisata?.kategori ?: "Unknown"}"

            if (category == "Seni dan Budaya") {
                Glide.with(itemView.context)
                    .load(randomSeniBudayaImage)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(ivCategoryPicture)
            } else if (category == "Hiburan") {
                Glide.with(itemView.context)
                    .load(randomHiburanImage)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(ivCategoryPicture)
            } else {
                Glide.with(itemView.context)
                    .load(randomAlamImage)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(ivCategoryPicture)
            }

            if (ratingDouble != null) {
                for (i in 1..displayRating) {
                    val star = createStarImageView(itemView.context, filled = true)
                    llRating.addView(star)
                }

                val emptyStars = maxStars - displayRating
                for (i in 1..emptyStars) {
                    val emptyStar = createStarImageView(itemView.context, filled = false)
                    llRating.addView(emptyStar)
                }

                llRating.visibility = View.VISIBLE
            } else {
                llRating.visibility = View.GONE
            }
        }

        private fun createStarImageView(context: Context, filled: Boolean): ImageView {
            val star = ImageView(context).apply {
                setImageResource(
                    if (filled) R.drawable.baseline_star_rate_24
                    else R.drawable.baseline_star_border_24
                )

                layoutParams = LinearLayout.LayoutParams(
                    dpToPx(24, context),
                    dpToPx(24, context)
                ).apply {
                    marginEnd = dpToPx(4, context)
                }

                contentDescription = if (filled) "Filled Star" else "Empty Star"
            }
            return star
        }

        private fun dpToPx(dp: Int, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }
    }

}
