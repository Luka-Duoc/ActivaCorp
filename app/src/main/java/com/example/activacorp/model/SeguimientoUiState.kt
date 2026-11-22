package com.example.activacorp.model

import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.data.local.entity.SeguimientoEntity
import com.example.activacorp.data.local.entity.UsuarioEntity

data class SeguimientoUiState(
    val seguimientos: List<SeguimientoEntity> = emptyList(),
    val oportunidadRelacionada: OportunidadEntity? = null,
    val usuarioResponsable: UsuarioEntity? = null,
    val tipoDeAccion: TipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
    val fecha: String = "",
    val hora: String = "",
    val resultado: Long = 0L,
    val proximaAccion: TipoDeAccion? = null,
    val fechaProxSeguimiento: String? = "",
    val observaciones: String? = "",
    val gestiones: List<GestionesPartConfirmada> = emptyList()
    )
