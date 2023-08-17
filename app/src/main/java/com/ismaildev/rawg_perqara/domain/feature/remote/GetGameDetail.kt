package com.ismaildev.rawg_perqara.domain.feature.remote

import com.ismaildev.rawg_perqara.data.model.DetailResponse
import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.model.GameListResponse
import com.ismaildev.rawg_perqara.domain.repository.Repository
import com.ismaildev.rawg_perqara.util.Resource
import kotlinx.coroutines.flow.Flow

class GetGameDetail(private val repository: Repository) {
    operator fun invoke(id:Int): Flow<Resource<DetailResponse>> {
        return repository.getDetailGame(id);
    }
}