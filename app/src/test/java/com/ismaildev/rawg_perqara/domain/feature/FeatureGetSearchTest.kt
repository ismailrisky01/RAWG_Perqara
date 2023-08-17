package com.ismaildev.rawg_perqara.domain.feature

import com.google.common.truth.Truth
import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.repository.FakeRemoteRepository
import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameList
import com.ismaildev.rawg_perqara.domain.feature.remote.GetSearch
import com.ismaildev.rawg_perqara.util.Status
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FeatureGetSearchTest {

    private lateinit var searchGame: GetSearch
    private lateinit var fakeRemoteRepository: FakeRemoteRepository

    @Before
    fun setUp(){
        fakeRemoteRepository = FakeRemoteRepository()
        searchGame = GetSearch(fakeRemoteRepository)


    }
    @Test
    fun `valid search feature exist`()= runBlocking{
        val gamesToInsert = mutableListOf<GameItem>()
        (1..5).forEach {
            gamesToInsert.add(GameItem(slug = "GTA Version $it"))
        }

        runBlocking {
            gamesToInsert.forEach {
                fakeRemoteRepository.insertGame(it)
            }
        }

        searchGame.invoke("GTA Version 3").collect{result->
            when (result.status) {
                Status.SUCCESS -> {
                    Truth.assertThat(result.data!!.isNotEmpty())
                }
                Status.ERROR -> {


                }
                Status.LOADING -> {


                }
            }
        }

    }

    @Test
    fun `valid search feature not exist`()= runBlocking{
        val gamesToInsert = mutableListOf<GameItem>()
        (1..5).forEach {
            gamesToInsert.add(GameItem(slug = "GTA Version $it"))
        }

        runBlocking {
            gamesToInsert.forEach {
                fakeRemoteRepository.insertGame(it)
            }
        }

        searchGame.invoke("GTA Version 7").collect{result->
            when (result.status) {
                Status.SUCCESS -> {
                    Truth.assertThat(result.data!!.isEmpty())
                }
                Status.ERROR -> {


                }
                Status.LOADING -> {


                }
            }
        }

    }
}