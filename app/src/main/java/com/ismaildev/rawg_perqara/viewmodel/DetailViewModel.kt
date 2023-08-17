package com.ismaildev.rawg_perqara.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.domain.usecase.DetailUseCase
import com.ismaildev.rawg_perqara.domain.usecase.HomeUseCase
import com.ismaildev.rawg_perqara.presentation.event.ScreenEvent
import com.ismaildev.rawg_perqara.presentation.state.ScreenState
import com.ismaildev.rawg_perqara.util.Status
import com.ismaildev.rawg_perqara.util.myLog
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(private val detailUseCase: DetailUseCase) : ViewModel() {
    private val _state = Channel<ScreenState>()
    val state = _state.receiveAsFlow()


    private var gameModel: ModelGame? = null
    fun onEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnShowGameDetail -> {
                getDetail(event.id)
            }
            is ScreenEvent.OnAddFavourite -> {
                saveAndDeleteFavorite()
            }
            is ScreenEvent.OnDeleteFavourite -> {
                saveAndDeleteFavorite()
            }
            else -> {}


        }
    }

    private fun getDetail(id: Int) {
        viewModelScope.launch {
            detailUseCase.getGameDetail.invoke(id).collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        myLog("Success Get Detail")
                        val data = result.data!!
                        gameModel = ModelGame(
                            data.name!!,
                            data.released!!,
                            data.rating.toString(),
                            data.id!!,
                            data.backgroundImage!!
                        )
                        sendUiState(ScreenState.OnShowDetailGame(data))
                        checkFavorite()
                    }
                    Status.ERROR -> {
                        myLog("Error")

                    }
                    Status.LOADING -> {
                        sendUiState(ScreenState.OnShowLoading)


                    }
                }
            }
        }
    }

    private fun checkFavorite() {
        myLog("Check Favorite 2")
        viewModelScope.launch {
            detailUseCase.getFavoriteByIdGame(gameModel!!.idGame).collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        if (result.data!!.isEmpty()) {
                            sendUiState(ScreenState.OnChangeFavoriteIcon(false))
                        } else {
                            myLog("Ada data")
                            sendUiState(ScreenState.OnChangeFavoriteIcon(true))

                        }

                    }
                    Status.ERROR -> {
                        myLog("Error")

                    }
                    Status.LOADING -> {

                    }
                }
            }

        }
    }


    private fun saveAndDeleteFavorite() {
        myLog("Check Favorite 2")
        viewModelScope.launch {
            detailUseCase.getFavoriteByIdGame(gameModel!!.idGame).collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        if (result.data!!.isEmpty()) {
                            saveFavourite()
                            sendUiState(ScreenState.OnChangeFavoriteIcon(false))
                        } else {
                            myLog("Ada data")
                            deleteFavorite(result.data.first().id!!)
                            sendUiState(ScreenState.OnChangeFavoriteIcon(true))

                        }

                    }
                    Status.ERROR -> {
                        myLog("Error")

                    }
                    Status.LOADING -> {

                    }
                }
            }

        }
    }
    private fun saveFavourite() {
        myLog("Save Favorite")
        viewModelScope.launch {
            detailUseCase.saveFavourite(modelGame = gameModel!!)
            checkFavorite()
        }
    }
    private fun deleteFavorite(id:Int) {
        myLog("Delete Favorite")
        viewModelScope.launch {
            detailUseCase.deleteFavorite.invoke(id)
            sendUiState(ScreenState.OnChangeFavoriteIcon(false))
            checkFavorite()

        }
    }

    private fun sendUiState(state: ScreenState) {
        viewModelScope.launch {
            _state.send(state)
        }
    }
}