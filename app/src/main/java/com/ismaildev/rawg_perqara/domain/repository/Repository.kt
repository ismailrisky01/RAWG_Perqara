package com.ismaildev.rawg_perqara.domain.repository

import com.ismaildev.rawg_perqara.data.model.DetailResponse
import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.model.GameListResponse
import com.ismaildev.rawg_perqara.data.model.SearchResponse
import com.ismaildev.rawg_perqara.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getGameList(): Flow<Resource<List<GameItem>>>
    fun getGameListLoadMore(page:String): Flow<Resource<List<GameItem>>>
    fun searchGame(search:String):Flow<Resource<List<GameItem>>>
    fun getByPage(pageSize:Int):Flow<Resource<GameListResponse>>
    fun getDetailGame(id:Int):Flow<Resource<DetailResponse>>
}