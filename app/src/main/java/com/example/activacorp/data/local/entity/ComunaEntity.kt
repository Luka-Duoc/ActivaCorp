package com.example.activacorp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comunas")
data class ComunaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String = ""
)