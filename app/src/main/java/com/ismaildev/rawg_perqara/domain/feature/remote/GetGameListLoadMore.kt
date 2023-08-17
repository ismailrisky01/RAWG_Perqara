package com.ismaildev.rawg_perqara.domain.feature.remote

import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.domain.repository.Repository
import com.ismaildev.rawg_perqara.util.Resource
import kotlinx.coroutines.flow.Flow

class GetGameListLoadMore(private val repository: Repository) {
    operator fun invoke(page:String): Flow<Resource<List<GameItem>>> {
        return repository.getGameListLoadMore(page)
    }
}