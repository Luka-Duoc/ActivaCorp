package com.example.activacorp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "eventos")
data class EventoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String = "",
    val tipo: String = "",
    val fechaInicio: LocalDate,
    val fechaTermino: LocalDate,
    val horario: String = "",
    val direccion: String = "",
    val comuna: ComunaEntity,
    val region: RegionEntity,
    val organizacion: OrganizacionEntity,
    val publicoEsperado: String = "",
    val publicoObjetivo: String = "",
    val cantAsistentes: Int = 0,
    val condiciones: String = "",
    val requisitos: String = "",
    val costosReferenciales: Int = 0,
    val espaciosDisponbiles: String = "",
    val observaciones: String = ""
)
