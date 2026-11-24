package com.example.activacorp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.local.entity.SeguimientoEntity
import com.example.activacorp.data.repository.OportunidadRepository
import com.example.activacorp.data.repository.ResultadoRepository
import com.example.activacorp.data.repository.SeguimientoRepository
import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.model.uiStates.SeguimientoUiState
import com.example.activacorp.model.enums.TipoDeAccion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SeguimientoViewModel(
    private val seguimientoRepository: SeguimientoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val oportunidadRepository: OportunidadRepository,
    private val resultadoRepository: ResultadoRepository
) : ViewModel() {
    private val _estado = MutableStateFlow(SeguimientoUiState())
    val estado: StateFlow<SeguimientoUiState> = _estado

    init {
        cargarSeguimientos()
        cargarUsuarios()
        cargarOportunidades()
        cargarResultados()
        obtenerGestionesPartConfirmada()
    }

    fun cargarSeguimientos() {
        viewModelScope.launch {
            seguimientoRepository.obtenerSeguimientos().collect { lista -> _estado.update {
                it.copy(seguimientos = lista)
            } }
        }
    }

    fun cargarOportunidades() {
        viewModelScope.launch {
            oportunidadRepository.obtenerTodas().collect { lista ->
                _estado.update { it.copy(oportunidadesDisponibles = lista) }
            }
        }
    }

    fun cargarResultados() {
        viewModelScope.launch {
            resultadoRepository.obtenerResultados().collect { lista ->
                _estado.update { it.copy(resultadoDisponible = lista) }
            }
        }
    }

    fun obtenerGestionesPartConfirmada() {
        viewModelScope.launch {
            seguimientoRepository.obtenerGestionesPartConfirmada().collect { lista -> _estado.update {
                it.copy(gestiones = lista)
            } }
        }
    }

    fun cargarUsuarios() {
        viewModelScope.launch {
            usuarioRepository.obtenerUsuarios().collect { lista -> _estado.update {
                it.copy(usuariosDisponibles = lista)
            } }
        }
    }

    fun onOportunidadChange(oportunidadId: Long) {
        _estado.update {
            it.copy(oportunidadRelacionada = oportunidadId)
        }
    }

    fun onUsuarioResponsableChange(usuarioId: Long?) {
        _estado.update {
            it.copy(usuarioResponsable = usuarioId)
        }
    }

    fun onTipoAccionChange(tipo: TipoDeAccion) {
        _estado.update {
            it.copy(tipoDeAccion = tipo)
        }
    }

    fun onFechaChange(fecha: String) {
        _estado.update {
            it.copy(fecha = fecha, errorFecha = null)
        }
    }

    fun onHora(hora: String) {
        _estado.update {
            it.copy(hora = hora, errorHora = null)
        }
    }

    fun onResultadoChange(resultadoId: Long) {
        _estado.update {
            it.copy(resultado = resultadoId)
        }
    }

    fun onProximaAccionChange(accion: TipoDeAccion) {
        _estado.update {
            it.copy(proximaAccion = accion)
        }
    }

    fun onFechaSeguimientoChange(proximo: String) {
        _estado.update {
            it.copy(fechaProxSeguimiento = proximo)
        }
    }

    fun onObservacionChange(observacion: String) {
        _estado.update {
            it.copy(observaciones = observacion)
        }
    }

    fun eliminar(seguimiento: SeguimientoEntity) {
        viewModelScope.launch {
            try {
                seguimientoRepository.eliminar(seguimiento)
            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        mensajeError = "Error al eliminar el seguimiento: ${e.message}"
                    )
                }
            }
        }
    }

    fun prepararEdicion(seguimiento: SeguimientoEntity) {
        _estado.update {
            it.copy(
                idEdicion = seguimiento.id,
                oportunidadRelacionada = seguimiento.oportunidadRelacionada,
                usuarioResponsable = seguimiento.usuarioResponsable,
                tipoDeAccion = seguimiento.tipoDeAccion,
                fecha = seguimiento.fecha,
                hora = seguimiento.hora,
                resultado = seguimiento.resultado,
                proximaAccion = seguimiento.proximaAccion,
                fechaProxSeguimiento = seguimiento.fechaProxSeguimiento,
                observaciones = seguimiento.observaciones,
                errorFecha = null,
                errorHora = null,
                mensajeError = null
            )
        }
    }

    fun guardarSeguimiento(onListo: () -> Unit) {
        val sg = _estado.value

        val errFecha = if (sg.fecha.isBlank()) "La fecha no puede estar vacia" else null
        val errHora = if (sg.hora.isBlank()) "La hora no puede estar vacia" else null

        if (errFecha != null || errHora != null) {
            _estado.update {
                it.copy(
                    errorFecha = errFecha,
                    errorHora = errHora
                )
            }
            return
        }

        if ((sg.oportunidadRelacionada ?: 0L) == 0L || (sg.usuarioResponsable ?: 0L) == 0L) {
            _estado.update {
                it.copy(mensajeError = "Debes seleccionar una oportunidad y un usuario responsable")
            }
            return
        }

        val seguimiento = SeguimientoEntity(
            id = sg.idEdicion ?: 0L,
            oportunidadRelacionada = sg.oportunidadRelacionada ?: 0L,
            usuarioResponsable = sg.usuarioResponsable ?: 0L,
            tipoDeAccion = sg.tipoDeAccion,
            fecha = sg.fecha,
            hora = sg.hora,
            resultado = if ((sg.resultado ?: 0L) == 0L) null else sg.resultado,
            proximaAccion = sg.proximaAccion ?: TipoDeAccion.EN_EVALUACION,
            fechaProxSeguimiento = sg.fechaProxSeguimiento,
            observaciones = sg.observaciones
        )

        viewModelScope.launch {
            try {
                if (sg.idEdicion != null) {
                    seguimientoRepository.actualizar(seguimiento)
                } else {
                    seguimientoRepository.insertar(seguimiento)
                }
                limpiarFormulario()
                onListo()
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = "Error al guardar el seguimiento: ${e.message}") }
            }
        }
    }

    fun limpiarFormulario() {
        _estado.update {
            it.copy(
                idEdicion = null,
                oportunidadRelacionada = 0L,
                usuarioResponsable = 0L,
                tipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
                fecha = "",
                hora = "",
                resultado = 0L,
                proximaAccion = TipoDeAccion.EN_EVALUACION,
                fechaProxSeguimiento = "",
                observaciones = "",
                errorFecha = null,
                errorHora = null,
                mensajeError = null
            )
        }
    }


}