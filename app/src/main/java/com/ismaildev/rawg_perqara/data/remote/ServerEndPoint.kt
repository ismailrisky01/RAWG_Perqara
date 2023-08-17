package com.ismaildev.rawg_perqara.data.remote


import com.ismaildev.rawg_perqara.data.model.DetailResponse
import com.ismaildev.rawg_perqara.data.model.GameListResponse
import com.ismaildev.rawg_perqara.data.model.SearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ServerEndPoint {
    @GET("games?")
    suspend fun getGameList(@Query("key") key:String): Response<GameListResponse>

    @GET("games?")
    suspend fun getGameListLoadMore(@Query("key") key:String,@Query("page") page:String): Response<GameListResponse>

    @GET("games?")
    suspend fun getByPage(@Query("page_size") page_size:Int,@Query("key") key:String): Response<GameListResponse>

    @GET("games?search=grand&key=76ef74d448004583920da2b719141b92")
    suspend fun getBySearch(@Query("search") search:String,@Query("key") key:String): Response<SearchResponse>

    @GET("games/{id}?")
    suspend fun getDetail(@Path(value = "id", encoded = true) id: Int?,@Query("key") key:String): Response<DetailResponse>



}