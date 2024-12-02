package com.example.capstoneproject.ui.automatic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AutomaticViewModel : ViewModel() {
    val text: LiveData<String> = MutableLiveData("Hello, World!")
}
