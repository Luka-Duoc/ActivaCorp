package com.example.activacorp.model.uiStates

import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.data.local.entity.ResultadoEntity
import com.example.activacorp.data.local.entity.SeguimientoEntity
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.model.GestionesPartConfirmada
import com.example.activacorp.model.enums.TipoDeAccion

data class SeguimientoUiState(
    val idEdicion: Long? = null,
    val seguimientos: List<SeguimientoEntity> = emptyList(),
    val oportunidadRelacionada: Long? = 0L,
    val usuarioResponsable: Long? = 0L,
    val tipoDeAccion: TipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
    val fecha: String = "",
    val hora: String = "",
    val resultado: Long? = 0L,
    val resultadoDisponible: List<ResultadoEntity> = emptyList(),
    val proximaAccion: TipoDeAccion? = null,
    val fechaProxSeguimiento: String? = "",
    val observaciones: String? = "",
    val usuariosDisponibles: List<UsuarioEntity> = emptyList(),
    val oportunidadesDisponibles: List<OportunidadEntity> = emptyList(),
    val gestiones: List<GestionesPartConfirmada> = emptyList(),

    // Errores
    val errorFecha: String? = null,
    val errorHora: String? = null,
    val mensajeError: String? = null
    )
