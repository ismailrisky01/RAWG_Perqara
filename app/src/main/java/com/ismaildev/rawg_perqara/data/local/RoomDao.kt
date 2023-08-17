package com.ismaildev.rawg_perqara.data.local

import androidx.room.*
import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.model.ModelGame

@Dao
interface RoomDao {
    @Query("SELECT * FROM modelgame")
    fun getGames():List<ModelGame>

    @Query("SELECT * FROM modelgame WHERE idGame = :idGame")
    fun getGamesById(idGame: Int): List<ModelGame>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(gameItem: ModelGame)

    @Query("DELETE FROM modelgame Where id=:id")
    fun deleteGame(id:Int)
}