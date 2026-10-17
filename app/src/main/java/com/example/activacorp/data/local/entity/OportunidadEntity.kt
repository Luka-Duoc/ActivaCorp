package com.example.activacorp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.activacorp.model.EstadoGestion
import com.example.activacorp.model.PotencialComercial
import com.example.activacorp.model.TipoOportunidad


@Entity(tableName = "oportunidades")
data class OportunidadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String = "",
    val tipo: TipoOportunidad,
    val descripcion: String = "",
    val fuente: String = "",
    val fechaDeteccion: String = "",
    val responsable: Long,
    val prioridad: Int,
    val potencial: PotencialComercial,
    val estado: EstadoGestion,
    val fechaActualizacion: String = "",
    val observaciones: String = ""
)
