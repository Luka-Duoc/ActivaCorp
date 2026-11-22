package com.example.activacorp.model

data class ResultadoUiState(
    val id: Long = 0,
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
