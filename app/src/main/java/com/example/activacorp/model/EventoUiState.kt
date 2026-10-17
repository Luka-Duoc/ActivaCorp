package com.example.activacorp.model

import com.example.activacorp.data.local.entity.EventoEntity

data class EventoUiState(
    val eventos: List<EventoEntity> = emptyList(),
    val nombre: String = "",
    val tipo: String = "",
    val fechaInicio: String = "",
    val fechaTermino: String = "",
    val horario: String = "",
    val direccion: String = "",
    val comunaId: Long = 1L,
    val regionId: Long = 1L,
    val organizacionId: Long = 1L,
    val publicoEsperado: String = "",
    val publicoObjetivo: String = "",
    val cantAsistentes: String = "0",
    val condiciones: String = "",
    val requisitos: String = "",
    val costosReferenciales: String = "0",
    val espaciosDisponibles: String = "",
    val observaciones: String = "",
    val mensajeError: String? = null,
    val exitSave: Boolean = false
)