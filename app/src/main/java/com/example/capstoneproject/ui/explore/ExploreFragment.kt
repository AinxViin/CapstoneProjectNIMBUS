package com.example.capstoneproject.ui.explore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.adapter.ProvinceAdapter
import com.example.capstoneproject.adapter.WisataCategoryAdapter
import com.example.capstoneproject.databinding.FragmentExploreBinding
import com.example.capstoneproject.di.Injection
import com.example.capstoneproject.ui.home.HomeViewModel

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var recommendationViewModel: RecommendationViewModel
    private lateinit var provinceAdapter: ProvinceAdapter
    private lateinit var categoryAdapter: WisataCategoryAdapter

    private var categoryId: Int? = null
    private var provinceId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        setupViewModels()
        setupRecycleView()
        setupView()
        setupButton()

        return binding.root
    }

    private fun setupButton() {
        binding.btnBackToProvince.setOnClickListener {
            recommendationViewModel.setProvinceId(null)
        }
    }

    private fun setupView() {
        // Observe provinceId LiveData
        recommendationViewModel.provinceId.observe(viewLifecycleOwner) { provincesId ->
            // Update visibility based on provinceId and categoryId
            provinceId = provincesId
            updateViewVisibility(provincesId, recommendationViewModel.categoryId.value)
        }

        // Observe categoryId LiveData
        recommendationViewModel.categoryId.observe(viewLifecycleOwner) { categorysId ->
            // Update visibility based on provinceId and categoryId
            categoryId = categorysId
            updateViewVisibility(recommendationViewModel.provinceId.value, categorysId)
        }
    }

    private fun updateViewVisibility(provinceId: Int?, categoryId: Int?) {
        if (provinceId == null && categoryId == null) {
            binding.layoutCategory.visibility = View.GONE
            binding.provincesLayout.visibility = View.VISIBLE
        } else {
            binding.layoutCategory.visibility = View.VISIBLE
            binding.provincesLayout.visibility = View.GONE
        }
    }


    private fun setupRecycleView() {
        provinceAdapter = ProvinceAdapter()
        categoryAdapter = WisataCategoryAdapter()

        binding.recyclerViewProvinces.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = provinceAdapter
        }

        binding.recyclerViewCategories.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = categoryAdapter
        }

        // Observer for provinces
        viewModel.provinces.observe(viewLifecycleOwner) { provinces ->
            provinceAdapter.submitList(provinces)
        }

        // Observer for categories
        viewModel.categoryWisata.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.submitList(categories)
        }

        binding.recyclerViewProvinces.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val childView = rv.findChildViewUnder(e.x, e.y)
                if (childView != null) {
                    val position = rv.getChildAdapterPosition(childView)
                    if (position != RecyclerView.NO_POSITION) {
                        val clickedCategory = provinceAdapter.provinceList[position]
                        recommendationViewModel.setProvinceId(clickedCategory.id)
                    }
                }
                return true
            }
        })

        binding.recyclerViewCategories.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val childView = rv.findChildViewUnder(e.x, e.y)
                if (childView != null) {
                    val position = rv.getChildAdapterPosition(childView)
                    if (position != RecyclerView.NO_POSITION) {
                        val clickedCategory = categoryAdapter.categoryList[position]
                        recommendationViewModel.setCategoryId(clickedCategory.id)

                        val name = "Liburan"
                        val budget = "0"
                        val date = "2024-12-25"

                        if (name.isEmpty() || budget.isEmpty() || date.isEmpty()) {
                            Toast.makeText(requireContext(), "All fields required", Toast.LENGTH_SHORT).show()
                        } else {
                            val intent = Intent(requireContext(), RecommendationActivity::class.java)
                            intent.putExtra("name", name)
                            intent.putExtra("provinceId", provinceId)
                            intent.putExtra("categoryId", clickedCategory.id)
                            intent.putExtra("budget", budget)
                            intent.putExtra("date", date)
                            intent.putExtra("picture", clickedCategory.thumbnail)
                            startActivity(intent)
                        }
                    }
                }
                return true
            }
        })
    }

    private fun setupViewModels() {
        val factory = Injection.provideViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        recommendationViewModel = ViewModelProvider(this, factory)[RecommendationViewModel::class.java]
        viewModel.getProvinces()
        viewModel.getCategoryWisata()
    }
}
