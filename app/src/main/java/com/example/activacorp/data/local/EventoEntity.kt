package com.example.activacorp.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "eventos",
    foreignKeys = [ForeignKey(entity = ComunaEntity::class,
        parentColumns = ["id"],
        childColumns = ["comuna"],
        onDelete = ForeignKey.CASCADE
    )
])
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
    val region: Long,
    val organizacion: Long,
    val publicoEsperado: String = "",
    val publicoObjetivo: String = "",
    val cantAsistentes: Int = 0,
    val condiciones: String = "",
    val requisitos: String = "",
    val costosReferenciales: Int = 0,
    val espaciosDisponbiles: String = "",
    val observaciones: String = ""
)
