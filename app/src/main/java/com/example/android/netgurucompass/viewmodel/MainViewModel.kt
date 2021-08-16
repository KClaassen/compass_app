package com.example.android.netgurucompass.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.netgurucompass.model.Coordinates
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {


    private lateinit var coordinates: MutableLiveData<Coordinates>

    init {
        println(getCoordinates().value)
    }

    open fun getCoordinates(): MutableLiveData<Coordinates> {
        if (!this::coordinates.isInitialized) {
            coordinates = MutableLiveData<Coordinates>()
        }
        if (coordinates == null) {
            coordinates = MutableLiveData<Coordinates>()
        }
        return coordinates
    }

    open fun setCoordinates(coord: Coordinates) {
        viewModelScope.launch {
            coordinates.value = coord
        }
    }
}