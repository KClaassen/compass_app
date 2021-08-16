package com.example.android.netgurucompass.intent

import com.example.android.netgurucompass.model.Coordinates

sealed class DataState {
    object Idle : DataState()
    data class Success(val coordinates : Coordinates) : DataState()
    data class Error(val exception: Exception) : DataState()
    object Loading : DataState()
}