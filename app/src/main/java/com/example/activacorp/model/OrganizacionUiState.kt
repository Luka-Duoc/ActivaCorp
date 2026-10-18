package com.example.activacorp.model

import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.local.entity.EventoEntity
import com.example.activacorp.data.local.entity.OrganizacionEntity

data class OrganizacionUiState(
    val idEnEdicion: Long? = null,
    val organizaciones: List<OrganizacionEntity> = emptyList(),
    val nombre: String = "",
    val tipo: TipoOrganizacion = TipoOrganizacion.OTROS,
    val rubro: String = "",
    val comunas: List<ComunaEntity> = emptyList(),
    val comunaSeleccionada: String = "",
    val comunaId: Long = 0L,
    val eventoId: Long = 0L,
    val listaEventos: List<EventoEntity> = emptyList(),
    val eventoSeleccionado: String = "",
    val sitioWeb: String? = "",
    val publico: String = "",
    val historial: String = "",
    val mensajeError: String? = null,
    val exitoGuardado: Boolean = false
)