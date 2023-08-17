package com.ismaildev.rawg_perqara.domain.feature.remote

import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.model.GameListResponse
import com.ismaildev.rawg_perqara.data.model.SearchResponse
import com.ismaildev.rawg_perqara.domain.repository.Repository
import com.ismaildev.rawg_perqara.util.Resource
import kotlinx.coroutines.flow.Flow

class GetSearch(private val repository: Repository) {
    operator fun invoke(key:String): Flow<Resource<List<GameItem>>> {
        return repository.searchGame(key)
    }
}