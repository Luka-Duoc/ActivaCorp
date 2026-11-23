package com.example.activacorp.model

import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.data.local.entity.SeguimientoEntity
import com.example.activacorp.data.local.entity.UsuarioEntity

data class SeguimientoUiState(
    val idEdicion: Long? = null,
    val seguimientos: List<SeguimientoEntity> = emptyList(),
    val oportunidadRelacionada: Long? = 0L,
    val usuarioResponsable: Long? = 0L,
    val tipoDeAccion: TipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
    val fecha: String = "",
    val hora: String = "",
    val resultado: Long? = 0L,
    val proximaAccion: TipoDeAccion? = null,
    val fechaProxSeguimiento: String? = "",
    val observaciones: String? = "",
    val gestiones: List<GestionesPartConfirmada> = emptyList(),

    // Errores
    val errorFecha: String? = null,
    val errorHora: String? = null,
    val mensajeError: String? = null
    )
