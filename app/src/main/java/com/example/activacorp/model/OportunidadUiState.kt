package com.example.activacorp.model

import com.example.activacorp.data.local.entity.EventoEntity
import com.example.activacorp.data.local.entity.OportunidadEntity

data class OportunidadUiState(
    val oportunidades: List<OportunidadEntity> = emptyList(),
    val oportunidadIdEnEdicion: Long? = null,
    val eventosDisponibles: List<EventoEntity> = emptyList(),
    val eventoId: Long = 0L,
    val nombre: String = "",
    val tipo: TipoOportunidad = TipoOportunidad.EVENTO_CORPORATIVO,
    val descripcion: String = "",
    val fuente: String = "",
    val fechaDeteccion: String = "",
    val responsable: Long = 0L,
    val prioridad: String = "1",
    val potencial: PotencialComercial = PotencialComercial.BAJO,
    val estado: EstadoGestion = EstadoGestion.EVENTO_IDENTIFICADO,
    val fechaActualizacion: String = "",
    val observaciones: String = "",
    val mensajeError: String? = null,
    val exitoGuardado: Boolean = false
)