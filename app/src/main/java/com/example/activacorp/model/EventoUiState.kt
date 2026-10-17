package com.example.activacorp.model

import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.local.entity.EventoEntity

data class EventoUiState(
    val eventos: List<EventoEntity> = emptyList(),
    val eventoIdEnEdicion: Long? = null,
    val nombre: String = "",
    val tipo: String = "",
    val fechaInicio: String = "",
    val fechaTermino: String = "",
    val horario: String = "",
    val direccion: String = "",
    val comunas: List<ComunaEntity> = emptyList(),
    val comunaSeleccionada: String = "",
    val comuna: Long = 1L,
    val organizacion: Long = 1L,
    val cantAsistentes: String = "0",
    val requisitos: String = "",
    val costosReferenciales: String = "0",
    val observaciones: String = "",
    val mensajeError: String? = null,
    val exitSave: Boolean = false
)