package com.example.capstoneproject.ui.explore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.adapter.RandomPlaceAdapter
import com.example.capstoneproject.databinding.ActivityRecommendationBinding
import com.example.capstoneproject.di.Injection
import com.example.capstoneproject.response.RecommendationRequest

class RecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendationBinding
    private lateinit var viewModel: RecommendationViewModel
    private lateinit var adapter: RandomPlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[RecommendationViewModel::class.java]

        val province = intent.getIntExtra("provinceId", 0)
        val category = intent.getIntExtra("categoryId", 0)
        val name = intent.getStringExtra("name")
        val budget = intent.getStringExtra("budget")
        val date = intent.getStringExtra("date")

        setupViewModel(province, category, name, budget, date)
        setupRecyclerView()
    }

    private fun setupViewModel(province: Int, category: Int, name: String?, budget: String?, date: String?) {
        viewModel.getRecommendation(
            request = RecommendationRequest(
                nama = name.toString(),
                provinsi_id = province,
                budget = budget.toString(),
                tanggal_perencanaan = date.toString(),
                categoryWisata_id = category
            )
        )
    }


    private fun setupRecyclerView() {
        adapter = RandomPlaceAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.recommendations.observe(this) { list ->
            val sortedData = list.sortedWith(compareByDescending {
                it.tempatWisata?.predictedRating ?: 0.0
            })

            adapter.submitList(sortedData)
        }
    }
}
