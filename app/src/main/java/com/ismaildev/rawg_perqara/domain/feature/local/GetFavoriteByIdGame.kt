package com.ismaildev.rawg_perqara.domain.feature.local

import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.domain.repository.LocalRepository
import com.ismaildev.rawg_perqara.util.Resource
import kotlinx.coroutines.flow.Flow

class GetFavoriteByIdGame(private val localRepository: LocalRepository) {
    operator fun invoke(idGame:Int): Flow<Resource<List<ModelGame>>> {
        return localRepository.getGamesByIdGame(idGame)
    }
}