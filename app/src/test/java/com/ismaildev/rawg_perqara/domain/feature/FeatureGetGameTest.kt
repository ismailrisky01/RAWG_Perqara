package com.ismaildev.rawg_perqara.domain.feature

import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.data.repository.FakeRemoteRepository
import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.ismaildev.rawg_perqara.domain.feature.remote.GetSearch
import com.ismaildev.rawg_perqara.presentation.state.ScreenState
import com.ismaildev.rawg_perqara.util.Status
import com.ismaildev.rawg_perqara.util.myLog

class FeatureGetGameTest {
    private lateinit var getGameList:GetGameList
    private lateinit var fakeRemoteRepository: FakeRemoteRepository

    @Before
    fun setUp(){
        fakeRemoteRepository = FakeRemoteRepository()
        getGameList = GetGameList(fakeRemoteRepository)


    }

    @Test
     fun `valid data already exist`()= runBlocking{
        val gamesToInsert = mutableListOf<GameItem>()
        (1..5).forEach {
            gamesToInsert.add(GameItem())
        }

        runBlocking {
            gamesToInsert.forEach {
                fakeRemoteRepository.insertGame(it)
            }
        }
        getGameList.invoke().collect{result->
            when (result.status) {
                Status.SUCCESS -> {
                   assertThat(result.data!!.isNotEmpty())
                }
                Status.ERROR -> {


                }
                Status.LOADING -> {


                }
            }
        }
    }

    @Test
    fun `valid data not already exist`()= runBlocking{
        getGameList.invoke().collect{result->
            when (result.status) {
                Status.SUCCESS -> {
                    assertThat(result.data!!.isEmpty())
                }
                Status.ERROR -> {


                }
                Status.LOADING -> {


                }
            }
        }
    }


}