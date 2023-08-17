package com.ismaildev.rawg_perqara.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ismaildev.rawg_perqara.data.model.ModelGame

@Database(
    entities = [ModelGame::class],
    version = 1
)
abstract class RoomDatabase :RoomDatabase() {
    abstract val roomDao:RoomDao

    companion object {
        const val DATABASE_NAME = "room_db"
    }
}