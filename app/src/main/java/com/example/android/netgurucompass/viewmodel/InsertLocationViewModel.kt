package com.example.android.netgurucompass.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.netgurucompass.intent.DataState
import com.example.android.netgurucompass.intent.Intent
import com.example.android.netgurucompass.model.Coordinates
import com.example.android.netgurucompass.utils.Localisation
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.lang.Exception

@ExperimentalCoroutinesApi
class InsertLocationViewModel() : ViewModel() {


    private lateinit var localisation: Localisation

    val userIntent = Channel<Intent>(Channel.UNLIMITED)

    private val _dataState= MutableStateFlow<DataState>(
            DataState.Idle)

    val dataState: StateFlow<DataState>
        get() = _dataState

    init {

        setDestinationEvent()
    }
    fun initShared(context: Context){
        localisation= Localisation(context)
    }

    private fun setDestinationEvent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is Intent.GetCoordinates -> {
                        getDestination()
                                .onEach {
                                    _dataState.value = it
                                }
                                .launchIn(viewModelScope)
                    }

                    Intent.None -> {
                    }
                }

            }


        }
    }
    private suspend fun getDestination(): Flow<DataState> = flow {
        emit(DataState.Loading)
        delay(300)
        try{
            var coordinates: Coordinates = Coordinates(localisation.getLatitude(),(localisation.getLongitude()))

            emit(DataState.Success(coordinates))

        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }

}