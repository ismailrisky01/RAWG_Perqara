package com.ismaildev.rawg_perqara.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ismaildev.rawg_perqara.domain.usecase.FavoriteUseCase
import com.ismaildev.rawg_perqara.presentation.event.ScreenEvent
import com.ismaildev.rawg_perqara.presentation.state.ScreenState
import com.ismaildev.rawg_perqara.util.Status
import com.ismaildev.rawg_perqara.util.myLog
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteUseCase: FavoriteUseCase) : ViewModel() {
    private val _state = Channel<ScreenState>()
    val state = _state.receiveAsFlow()
    fun onEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnShowFavorite -> {
                getFavorite()
            }
            else -> {}


        }
    }

     private fun getFavorite() {
        myLog("GetFavourite")
        viewModelScope.launch {
            favoriteUseCase.getFavourite.invoke().collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        myLog("Success")
                        Log.d("IsmailLog", "ada"+result.data.toString())
                        sendUiState(ScreenState.OnShowFavorite(result.data!!))
                    }
                    Status.ERROR -> {
                        myLog("Error")
                        Log.d("IsmailLog", result.message.toString())


                    }
                    Status.LOADING -> {
                        myLog("Loading")

                        Log.d("IsmailLog", "LOADING")

                    }
                }
            }
        }
    }

    private fun sendUiState(state: ScreenState) {
        viewModelScope.launch {
            _state.send(state)
        }
    }
}