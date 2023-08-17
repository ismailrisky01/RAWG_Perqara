package com.ismaildev.rawg_perqara.domain.repository

import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.util.Resource
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    fun getGames(): Flow<Resource<List<ModelGame>>>
    fun getGamesByIdGame(idGame:Int):Flow<Resource<List<ModelGame>>>
    suspend fun insert(modelGame: ModelGame)
    suspend fun delete(id: Int)
}