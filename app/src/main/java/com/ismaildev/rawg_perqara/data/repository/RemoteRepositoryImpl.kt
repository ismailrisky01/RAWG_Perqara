package com.ismaildev.rawg_perqara.data.repository

import com.ismaildev.rawg_perqara.data.model.DetailResponse
import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.model.GameListResponse
import com.ismaildev.rawg_perqara.data.model.SearchResponse
import com.ismaildev.rawg_perqara.data.remote.ServerEndPoint
import com.ismaildev.rawg_perqara.domain.repository.Repository
import com.ismaildev.rawg_perqara.util.Resource
import com.ismaildev.rawg_perqara.util.myLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class RemoteRepositoryImpl(private val serverEndPoint: ServerEndPoint):Repository {
    override fun getGameList(): Flow<Resource<List<GameItem>>> {
        return flow{
            try {
                val result = serverEndPoint.getGameList(key = "76ef74d448004583920da2b719141b92")
                emit(Resource.loading(null))
                if(result.isSuccessful){
                    emit(Resource.success(result.body()!!.results))
                }else{
                    try {
                        val jObjError = JSONObject(result.errorBody()!!.toString())
                        val message = jObjError.getJSONObject("error").getString("message")
                        emit(Resource.error(message, null))
                    } catch (e: Exception) {
                        myLog("Error Ges "+e.message.toString())
                    }
                }

            }catch (e:Exception){
                myLog("Error getGift Jaringan")
                emit(
                    Resource.error(
                        "Mohon maaf terjadi kesalahan Jaringan, Silahkan Coba beberapa saat lagi",
                        null
                    )
                )
            }
        }
    }

    override fun getGameListLoadMore(page:String): Flow<Resource<List<GameItem>>> {
        return flow{
            try {
                val result = serverEndPoint.getGameListLoadMore(key = "76ef74d448004583920da2b719141b92", page = page)
                emit(Resource.loading(null))
                if(result.isSuccessful){
                    emit(Resource.success(result.body()!!.results))
                }else{
                    try {
                        val jObjError = JSONObject(result.errorBody()!!.toString())
                        val message = jObjError.getJSONObject("error").getString("message")
                        emit(Resource.error(message, null))
                    } catch (e: Exception) {
                        myLog("Error Ges "+e.message.toString())
                    }
                }

            }catch (e:Exception){
                myLog("Error getGift Jaringan")
                emit(
                    Resource.error(
                        "Mohon maaf terjadi kesalahan Jaringan, Silahkan Coba beberapa saat lagi",
                        null
                    )
                )
            }
        }
    }

    override fun searchGame(search: String): Flow<Resource<List<GameItem>>> {
        return flow {
            try {
                val result = serverEndPoint.getBySearch(search = search, key = "76ef74d448004583920da2b719141b92")
                emit(Resource.loading(null))
                if(result.isSuccessful){
                    emit(Resource.success(result.body()!!.results))
                }else{
                    try {
                        val jObjError = JSONObject(result.errorBody()!!.toString())
                        val message = jObjError.getJSONObject("error").getString("message")
                        emit(Resource.error(message, null))
                    } catch (e: Exception) {
                        myLog("Error Ges "+e.message.toString())
                    }
                }

            }catch (e:Exception){
                myLog("Error getGift Jaringan")
                emit(
                    Resource.error(
                        "Mohon maaf terjadi kesalahan Jaringan, Silahkan Coba beberapa saat lagi",
                        null
                    )
                )
            }

        }
    }

    override fun getByPage(pageSize: Int): Flow<Resource<GameListResponse>> {
        TODO("Not yet implemented")
    }

    override fun getDetailGame(id:Int): Flow<Resource<DetailResponse>> {
        return flow{
            try {
                val result = serverEndPoint.getDetail(id = id, key = "76ef74d448004583920da2b719141b92")
                emit(Resource.loading(null))
                if(result.isSuccessful){
                    emit(Resource.success(result.body()))
                }else{
                    try {
                        val jObjError = JSONObject(result.errorBody()!!.toString())
                        val message = jObjError.getJSONObject("error").getString("message")
                        emit(Resource.error(message, null))
                    } catch (e: Exception) {
                        myLog("Error Ges "+e.message.toString())
                    }
                }

            }catch (e:Exception){
                myLog("Error getGift Jaringan")
                emit(
                    Resource.error(
                        "Mohon maaf terjadi kesalahan Jaringan, Silahkan Coba beberapa saat lagi",
                        null
                    )
                )
            }
        }
    }
}