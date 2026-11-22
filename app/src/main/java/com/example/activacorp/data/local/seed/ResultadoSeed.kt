package com.example.activacorp.data.local.seed

import com.example.activacorp.data.local.entity.ResultadoEntity
import com.example.activacorp.model.EstadoGestion

object ResultadoSeed {
    val listaResultados = listOf(
        ResultadoEntity(
            id = 1L,
            fechaRealizacion = "20/10/2026",
            estadoFinal = EstadoGestion.PARTICIPACION_CONFIRMADA,
            cantidadEstimadaAsistentes = 15,
            cantidadContactosGenerados = 4,
            cantidadPersonasInteresadas = 15,
            cantidadInscripciones = 18,
            observaciones = "Ninguna",
            evaluacionGeneral = "Perfecto.",
            aprendizajes = "Boca es un sentimiento."
        ),
    )
}