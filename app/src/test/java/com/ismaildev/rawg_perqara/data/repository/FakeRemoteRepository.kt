package com.ismaildev.rawg_perqara.data.repository

import com.ismaildev.rawg_perqara.data.model.DetailResponse
import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.model.GameListResponse
import com.ismaildev.rawg_perqara.data.model.SearchResponse
import com.ismaildev.rawg_perqara.domain.repository.Repository
import com.ismaildev.rawg_perqara.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRemoteRepository:Repository {
    private val games = mutableListOf<GameItem>()

    override fun getGameList(): Flow<Resource<List<GameItem>>> {
        return flow { emit(Resource.success(games)) }
    }

    override fun searchGame(search: String): Flow<Resource<List<GameItem>>> {
        return flow { val newList = games.filter {
            it.slug!!.contains(search)
        }
        emit(Resource.success(newList))
        }
    }

    override fun getByPage(pageSize: Int): Flow<Resource<GameListResponse>> {
        TODO("Not yet implemented")
    }

    override fun getDetailGame(id: Int): Flow<Resource<DetailResponse>> {
        return flow {

        }
    }

    suspend fun insertGame(gameItem: GameItem){
        games.add(gameItem)
    }

}