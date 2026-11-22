package com.example.activacorp.model

import com.example.activacorp.data.local.entity.ResultadoEntity

data class ResultadoUiState(
    val resultados: List<ResultadoEntity> = emptyList(),
    val fechaRealizacion: String = "",
    val estadoFinal: EstadoGestion? = null,
    val cantidadEstimadaAsistentes: Int = 0,
    val cantidadContactosGenerados: Int = 0,
    val cantidadPersonasInteresadas: Int = 0,
    val cantidadInscripciones: Int = 0,
    val observaciones: String? = "",
    val evaluacionGeneral: String = "",
    val aprendizajes: String? = ""
)
