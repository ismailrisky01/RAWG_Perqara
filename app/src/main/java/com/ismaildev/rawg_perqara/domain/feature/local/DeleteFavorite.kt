package com.ismaildev.rawg_perqara.domain.feature.local

import com.ismaildev.rawg_perqara.data.model.ModelGame
import com.ismaildev.rawg_perqara.domain.repository.LocalRepository

class DeleteFavorite(private val localRepository: LocalRepository) {
    suspend operator fun invoke(id: Int) {
        localRepository.delete(id)
    }
}