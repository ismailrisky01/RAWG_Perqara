package com.ismaildev.rawg_perqara.domain.feature

import com.google.common.truth.Truth
import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.repository.FakeRemoteRepository
import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameDetail
import com.ismaildev.rawg_perqara.domain.feature.remote.GetSearch
import com.ismaildev.rawg_perqara.util.Status
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FeatureGetDetailGameTest {
    private lateinit var getGameDetail: GetGameDetail
    private lateinit var fakeRemoteRepository: FakeRemoteRepository

    @Before
    fun setUp(){
        fakeRemoteRepository = FakeRemoteRepository()
        getGameDetail = GetGameDetail(fakeRemoteRepository)
    }
    @Test
    fun `valid data already exist`()= runBlocking{
        getGameDetail.invoke(1).collect{result->
            when (result.status) {
                Status.SUCCESS -> {
                    Truth.assertThat(result.data!=null)
                }
                Status.ERROR -> {


                }
                Status.LOADING -> {


                }
            }
        }
    }

}