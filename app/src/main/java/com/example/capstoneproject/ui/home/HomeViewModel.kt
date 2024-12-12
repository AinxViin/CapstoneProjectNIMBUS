package com.example.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.UserRepository
import com.example.capstoneproject.response.ProvinceResponse
import com.example.capstoneproject.response.WisataCategoryResponseItem
import com.example.capstoneproject.response.WisataResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    // LiveData untuk provinsi
    private val _provinces = MutableLiveData<List<ProvinceResponse>>()
    val provinces: LiveData<List<ProvinceResponse>> = _provinces

    // LiveData untuk wisata alam
    private val _wisataAlam = MutableLiveData<List<WisataResponse>>()
    val wisataAlam: LiveData<List<WisataResponse>> = _wisataAlam

    // LiveData untuk seni dan budaya
    private val _wisataBudaya = MutableLiveData<List<WisataResponse>>()
    val wisataBudaya: LiveData<List<WisataResponse>> = _wisataBudaya

    // LiveData untuk wisata hiburan
    private val _wisataHiburan = MutableLiveData<List<WisataResponse>>()
    val wisataHiburan: LiveData<List<WisataResponse>> = _wisataHiburan

    // LiveData untuk kategori
    private val _categoryWisata = MutableLiveData<List<WisataCategoryResponseItem>>()
    val categoryWisata: LiveData<List<WisataCategoryResponseItem>> = _categoryWisata

    // LiveData untuk loading dan error
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getProvinces() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.getProvinces()
                _provinces.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error fetching provinces"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getWisata() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.getWisata()
                val alam = response.filter { it.categoryWisata_id == 1 }.distinctBy { it.id }
                val budaya = response.filter { it.categoryWisata_id == 2 }.distinctBy { it.id }
                val hiburan = response.filter { it.categoryWisata_id == 3 }.distinctBy { it.id }

                _wisataAlam.value = alam
                _wisataBudaya.value = budaya
                _wisataHiburan.value = hiburan
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error fetching wisata"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getCategoryWisata() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.getCategoryWisata()

                _categoryWisata.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error fetching wisata"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
