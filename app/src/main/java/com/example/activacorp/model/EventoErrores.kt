package com.example.activacorp.model

import java.time.LocalDate

data class EventoErrores(
    val nombre: String? = null,
    val tipo: String? = null,
    val descripcion: String? = null,
    val fechaInicio: LocalDate? = null,
    val fechaTermino: LocalDate? = null,
    val direccion: String? = null,
    val comuna: String? = null,
    val region: String? = null,
    val organizacion: String? = null,
    val publicoObjetivo: String? = null,
    val cantidadAsistentes: Int? = null,
    val condicionesParticipacion: String? = null,
    val requisitos: String? = null,
    val costosReferenciales: Int? = null,
    val espaciosDisponibles: Int? = null,
    val observacions: String? = null
)
