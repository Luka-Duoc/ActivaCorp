package com.example.activacorp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "comunas",
    foreignKeys = [
        ForeignKey(
            entity = RegionEntity::class,
            parentColumns = ["id"],
            childColumns = ["idRegion"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class ComunaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val idRegion: Long,
    val nombre: String = ""
)