package com.example.activacorp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regiones")
data class RegionEntity(
    @PrimaryKey
    val id: Long = 0,
    val nombre: String = ""
)
