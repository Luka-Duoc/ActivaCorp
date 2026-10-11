package com.example.activacorp.model

import java.time.LocalDate

data class EventoUiState(
    val nombre: String,
    val tipo: String,
    val descripcion: String,
    val fechaInicio: LocalDate,
    val fechaTermino: LocalDate,
    val direccion: String,
    val comuna: String,
    val region: String,
    val organizacion: String,
    val publicoObjetivo: String,
    val cantidadAsistentes: Int,
    val condicionesParticipacion: String,
    val requisitos: String,
    val costosReferenciales: Int,
    val espaciosDisponibles: Int,
    val observacions: String,
    val errores: EventoErrores = EventoErrores()
)
