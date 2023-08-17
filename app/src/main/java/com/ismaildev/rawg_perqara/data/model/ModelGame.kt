package com.ismaildev.rawg_perqara.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModelGame (
    val title: String,
    val released: String,
    val rating: String,
    val idGame:Int,
    val background:String,
    @PrimaryKey val id: Int? = null
)