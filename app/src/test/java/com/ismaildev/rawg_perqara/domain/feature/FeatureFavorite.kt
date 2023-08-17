package com.ismaildev.rawg_perqara.domain.feature

import com.google.common.truth.Truth
import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.data.repository.FakeLocalRepository
import com.ismaildev.rawg_perqara.data.repository.FakeRemoteRepository
import com.ismaildev.rawg_perqara.domain.feature.local.DeleteFavorite
import com.ismaildev.rawg_perqara.domain.feature.local.GetFavorite
import com.ismaildev.rawg_perqara.domain.feature.local.GetFavoriteByIdGame
import com.ismaildev.rawg_perqara.domain.feature.local.SaveFavourite
import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameDetail
import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameList
import com.ismaildev.rawg_perqara.util.Status
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FeatureFavorite {
    private lateinit var getFavorite:GetFavorite
    private lateinit var getFavoriteByIdGame: GetFavoriteByIdGame
    private lateinit var saveFavourite: SaveFavourite
    private lateinit var deleteFavorite: DeleteFavorite
    private lateinit var fakeLocalRepository: FakeLocalRepository

    @Before
    fun setUp(){
        fakeLocalRepository = FakeLocalRepository()
        getFavorite = GetFavorite(fakeLocalRepository)
        getFavoriteByIdGame = GetFavoriteByIdGame(fakeLocalRepository)
        saveFavourite = SaveFavourite(fakeLocalRepository)
        deleteFavorite = DeleteFavorite(fakeLocalRepository)
        getFavorite = GetFavorite(fakeLocalRepository)
        val favoriteToInsert = mutableListOf<ModelGame>()

        (1..5).forEach {
            favoriteToInsert.add(ModelGame("Game","12-01","5.0",100+it,"",it))
        }

        runBlocking {
            favoriteToInsert.forEach {
                fakeLocalRepository.insert(it)
            }
        }
    }

    @Test
    fun `valid get Favorite`()= runBlocking{
        getFavorite.invoke().collect{result->
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
    fun `valid insert Favorite`()= runBlocking{
        saveFavourite.invoke(ModelGame("","","",200,"",10))
        getFavorite.invoke().collect{result->
            when (result.status) {
                Status.SUCCESS -> {
                    Truth.assertThat(result.data!!.size>5)
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {


                }
            }
        }

    }
    @Test
    fun `valid delete Favorite`()= runBlocking{
        deleteFavorite.invoke(1)
        getFavorite.invoke().collect{result->
            when (result.status) {
                Status.SUCCESS -> {
                    Truth.assertThat(result.data!!.size!=5)
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {


                }
            }
        }

    }
}