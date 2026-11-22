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
        ResultadoEntity(
            id = 2L,
            fechaRealizacion = "02/11/2026",
            estadoFinal = EstadoGestion.EVENTO_IDENTIFICADO,
            cantidadEstimadaAsistentes = 45,
            cantidadContactosGenerados = 6,
            cantidadPersonasInteresadas = 32,
            cantidadInscripciones = 25,
            observaciones = "Alta participación en charla de salud preventiva.",
            evaluacionGeneral = "Muy buena recepción del equipo de recursos humanos.",
            aprendizajes = "Agendar demostraciones prácticas aumenta el interés inicial."
        ),
        ResultadoEntity(
            id = 3L,
            fechaRealizacion = "06/11/2026",
            estadoFinal = EstadoGestion.EN_ESPERA_DE_RESPUESTA,
            cantidadEstimadaAsistentes = 30,
            cantidadContactosGenerados = 3,
            cantidadPersonasInteresadas = 18,
            cantidadInscripciones = 12,
            observaciones = "Faltó confirmar disponibilidad de sala para el próximo mes.",
            evaluacionGeneral = "Jornada cumplida según cronograma.",
            aprendizajes = "Solicitar confirmación de espacio físico con 1 semana de anticipación."
        ),
        ResultadoEntity(
            id = 4L,
            fechaRealizacion = "12/11/2026",
            estadoFinal = EstadoGestion.PARTICIPACION_CONFIRMADA,
            cantidadEstimadaAsistentes = 120,
            cantidadContactosGenerados = 11,
            cantidadPersonasInteresadas = 85,
            cantidadInscripciones = 74,
            observaciones = "Operativo masivo en casa matriz sin inconvenientes.",
            evaluacionGeneral = "Excelente convocatoria y satisfacción de los asistentes.",
            aprendizajes = "Reforzar equipo de monitores cuando superan los 100 asistentes."
        ),
        ResultadoEntity(
            id = 5L,
            fechaRealizacion = "17/11/2026",
            estadoFinal = EstadoGestion.EN_ESPERA_DE_RESPUESTA,
            cantidadEstimadaAsistentes = 60,
            cantidadContactosGenerados = 5,
            cantidadPersonasInteresadas = 40,
            cantidadInscripciones = 28,
            observaciones = "En espera de firma de convenio trimestral.",
            evaluacionGeneral = "Buen desempeño técnico del equipo.",
            aprendizajes = "Entregar resumen ejecutivo el mismo día acelera la respuesta."
        ),
        ResultadoEntity(
            id = 6L,
            fechaRealizacion = "20/11/2026",
            estadoFinal = EstadoGestion.PARTICIPACION_CONFIRMADA,
            cantidadEstimadaAsistentes = 80,
            cantidadContactosGenerados = 8,
            cantidadPersonasInteresadas = 62,
            cantidadInscripciones = 55,
            observaciones = "Se coordinó segunda fecha para turno nocturno.",
            evaluacionGeneral = "Muy positivo, cliente interesado en plan anual.",
            aprendizajes = "Incluir horarios flexibles amplía la cobertura en empresas operativas."
        )
    )
}