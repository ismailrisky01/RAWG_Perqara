package com.ismaildev.rawg_perqara.data.repository

import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.domain.repository.LocalRepository
import com.ismaildev.rawg_perqara.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalRepository():LocalRepository {
    private val games = mutableListOf<ModelGame>()
    override fun getGames(): Flow<Resource<List<ModelGame>>> {
        return flow{
            emit(Resource.success(games))
        }
    }

    override fun getGamesByIdGame(idGame: Int): Flow<Resource<List<ModelGame>>> {
        return flow {
            val newList = games.filter { it.idGame == idGame }
            emit(Resource.success(newList))
        }
    }

    override suspend fun insert(modelGame: ModelGame) {
        games.add(modelGame)
    }

    override suspend fun delete(id: Int) {
        games.removeIf {
            it.id==id
        }
    }
}