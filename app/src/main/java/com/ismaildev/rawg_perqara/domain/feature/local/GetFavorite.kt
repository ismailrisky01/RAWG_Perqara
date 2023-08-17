package com.ismaildev.rawg_perqara.domain.feature.local

import com.ismaildev.rawg_perqara.data.model.DetailResponse
import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.domain.repository.LocalRepository
import com.ismaildev.rawg_perqara.domain.repository.Repository
import com.ismaildev.rawg_perqara.util.Resource
import kotlinx.coroutines.flow.Flow

class GetFavorite(private val localRepository: LocalRepository) {
    operator fun invoke(): Flow<Resource<List<ModelGame>>> {
        return localRepository.getGames()
    }
}