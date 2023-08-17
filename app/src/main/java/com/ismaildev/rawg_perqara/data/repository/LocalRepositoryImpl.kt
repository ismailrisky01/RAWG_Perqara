package com.ismaildev.rawg_perqara.data.repository

import com.ismaildev.rawg_perqara.data.local.RoomDao
import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.domain.repository.LocalRepository
import com.ismaildev.rawg_perqara.util.Resource
import com.ismaildev.rawg_perqara.util.myLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalRepositoryImpl(private val roomDao: RoomDao):LocalRepository {
    override fun getGames(): Flow<Resource<List<ModelGame>>> {
        return flow {
            emit(Resource.loading(null))
            emit(Resource.success(roomDao.getGames()))
        }
    }

    override fun getGamesByIdGame(idGame: Int): Flow<Resource<List<ModelGame>>> {
        return flow {
            emit(Resource.loading(null))
            emit(Resource.success(roomDao.getGamesById(idGame)))
        }
    }


    override suspend fun insert(modelGame: ModelGame) {
        myLog("Save Room")
        roomDao.insertGame(modelGame)
    }

    override suspend fun delete(id: Int) {
        myLog("Delete Room")
        roomDao.deleteGame(id)
    }
}