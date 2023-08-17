package com.ismaildev.rawg_perqara.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.domain.usecase.HomeUseCase
import com.ismaildev.rawg_perqara.presentation.event.ScreenEvent
import com.ismaildev.rawg_perqara.presentation.state.ScreenState
import com.ismaildev.rawg_perqara.util.Status
import com.ismaildev.rawg_perqara.util.myLog
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _state = Channel<ScreenState>()
    val state = _state.receiveAsFlow()
    private val _page = MutableStateFlow(1)
    val page = _page.asStateFlow()

    private val listGame = ArrayList<GameItem>()

    fun onEvent(event: ScreenEvent) {
        when (event) {
            is ScreenEvent.OnSearch -> {
                searchGameApi(event.key)
            }
            is ScreenEvent.OnShowGameList -> {
                getGame()
            }
            is ScreenEvent.OnLoadMore -> {
                loadMore()
            }

            else -> {

            }


        }
    }

    private fun getGame() {
        viewModelScope.launch {
            homeUseCase.getGameList.invoke().collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        myLog("Success")
                        listGame.clear()
                        listGame.addAll(result.data!!)
                        sendUiState(ScreenState.OnShowGame(result.data))
                    }
                    Status.ERROR -> {
                        myLog("Error")
                        sendUiState(ScreenState.OnShowError(result.message.toString()))
                    }
                    Status.LOADING -> {
                        sendUiState(ScreenState.OnShowLoading)

                    }
                }

            }
        }
    }

    private fun searchGameApi(key: String) {
        viewModelScope.launch {
            homeUseCase.getSearch.invoke(key).collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        myLog("Success")
                        listGame.clear()
                        listGame.addAll(result.data!!)
                        sendUiState(ScreenState.OnShowGame(result.data))
                    }
                    Status.ERROR -> {
                        myLog("Error")
                        sendUiState(ScreenState.OnShowError(result.message.toString()))
                    }
                    Status.LOADING -> {
                        sendUiState(ScreenState.OnShowLoading)

                    }
                }

            }
        }


    }

    private fun searchGame(key: String) {
        val data = listGame.filter {
            it.slug!!.contains(key)
        }
        sendUiState(ScreenState.OnShowGame(data))
    }

    private fun loadMore() {
        _page.value = page.value + 1
        viewModelScope.launch {
            homeUseCase.getGameListLoadMore.invoke(page.value.toString()).collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        myLog("Success")
                        listGame.addAll(result.data!!)
                        sendUiState(ScreenState.OnShowGameLoadMore(listGame))
                    }
                    Status.ERROR -> {
                        myLog("Error")
                        sendUiState(ScreenState.OnShowError(result.message.toString()))

                    }
                    Status.LOADING -> {
                        sendUiState(ScreenState.OnShowLoading)

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