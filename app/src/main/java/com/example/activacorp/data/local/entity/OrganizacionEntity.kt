package com.example.activacorp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.activacorp.model.TipoOrganizacion

@Entity(
    tableName = "organizaciones",
    foreignKeys = [
        ForeignKey(
            entity = ComunaEntity::class,
            parentColumns = ["id"],
            childColumns = ["comuna"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EventoEntity::class,
            parentColumns = ["id"],
            childColumns = ["eventoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["comuna"]),
        Index(value = ["eventoId"])
    ]
    )
data class OrganizacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String = "",
    val tipo: TipoOrganizacion,
    val rubro: String = "",
    val comuna: Long,
    val sitioWeb: String? = "",
    val publico: String = "",
    val historial: String = "",
    val eventoId: Long = 0L
)