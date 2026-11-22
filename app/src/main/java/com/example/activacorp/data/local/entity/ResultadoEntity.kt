package com.example.activacorp.data.local.entity

import androidx.room.Entity
import com.example.activacorp.model.EstadoGestion

@Entity(tableName = "resultados")
data class ResultadoEntity(
    val id: Long = 0,
    val fechaRealizacion: String,
    val estadoFinal: EstadoGestion,
    val cantidadEstimadaAsistentes: Int,
    val cantidadContactosGenerados: Int,
    val cantidadPersonasInteresadas: Int,
    val cantidadInscripciones: Int,
    val observaciones: String?,
    val evaluacionGeneral: String,
    val aprendizajes: String?
)
