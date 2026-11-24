package com.example.activacorp.model.uiStates

import com.example.activacorp.data.local.entity.EventoEntity
import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.model.OportunidadConEjecutivo
import com.example.activacorp.model.OportunidadConOrganizacion
import com.example.activacorp.model.enums.EstadoGestion
import com.example.activacorp.model.enums.PotencialComercial
import com.example.activacorp.model.enums.TipoOportunidad

data class OportunidadUiState(
    val oportunidades: List<OportunidadEntity> = emptyList(),
    val oportunidadIdEnEdicion: Long? = null,
    val eventosDisponibles: List<EventoEntity> = emptyList(),
    val usuariosDisponibles: List<UsuarioEntity> = emptyList(),
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
    val exitoGuardado: Boolean = false,
    val cantPendientes: Int = 0,
    val cantPrioridadAlta: Int = 0,
    val cantConfirmadas: Int = 0,
    val oportunidadesAltas: List<OportunidadConOrganizacion> = emptyList(),
    val oportunidadUsuario: List<OportunidadConEjecutivo> = emptyList(),

    // Errores
    val errorNombre: String? = null,
    val errorDescripcion: String? = null,
    val errorFechaDeteccion: String? = null,
    val errorPrioridad: String? = null,
    val errorFuente: String? = null,
    val mensajeError: String? = null,
)