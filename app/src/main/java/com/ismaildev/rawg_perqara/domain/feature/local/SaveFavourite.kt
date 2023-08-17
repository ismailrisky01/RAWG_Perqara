package com.ismaildev.rawg_perqara.domain.feature.local

import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.domain.repository.LocalRepository
import com.ismaildev.rawg_perqara.util.Resource
import com.ismaildev.rawg_perqara.util.myLog
import kotlinx.coroutines.flow.Flow

class SaveFavourite(private val localRepository: LocalRepository) {
    suspend operator fun invoke(modelGame: ModelGame) {
         localRepository.insert(modelGame)
    }
}