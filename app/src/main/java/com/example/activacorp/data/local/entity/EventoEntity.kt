package com.example.activacorp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "eventos",
    foreignKeys = [
        ForeignKey(
            entity = ComunaEntity::class,
            parentColumns = ["id"],
            childColumns = ["comuna"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["comuna"])
    ]
)
data class EventoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String = "",
    val tipo: String = "",
    val fechaInicio: String,
    val fechaTermino: String,
    val horario: String = "",
    val direccion: String = "",
    val comuna: Long,
    val organizacion: Long,
    val cantAsistentes: Int = 0,
    val requisitos: String = "",
    val costosReferenciales: Int = 0,
    val observaciones: String = ""
)
