package com.example.activacorp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.data.repository.EventoRepository
import com.example.activacorp.data.repository.OportunidadRepository
import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.model.EstadoGestion
import com.example.activacorp.model.OportunidadUiState
import com.example.activacorp.model.PotencialComercial
import com.example.activacorp.model.TipoOportunidad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OportunidadViewModel(
    private val oportunidadRepository: OportunidadRepository,
    private val eventoRepository: EventoRepository,
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _estado = MutableStateFlow(OportunidadUiState())
    val estado: StateFlow<OportunidadUiState> = _estado.asStateFlow()

    init {
        cargarOportunidades()
        cargarEventos()
        cargarUsuarios()
        contarPendientes()
        contarPrioridadAlta()
        contarConfirmadas()
        obtenerOpPrioridadAlta()
        obtenerOportunidadesPorUsuario()
    }

    fun cargarUsuarios() {
        viewModelScope.launch {
            usuarioRepository.obtenerUsuarios()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar usuarios: ${e.message}") }
                }
                .collect { lista ->
                    _estado.update {
                        it.copy(usuariosDisponibles = lista)
                    }
                }
        }
    }

    private fun cargarEventos() {
        viewModelScope.launch {
            eventoRepository.obtenerTodos()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar eventos: ${e.message}") }
                }
                .collect { listaEventos ->
                    _estado.update {
                        it.copy(
                            eventosDisponibles = listaEventos,
                            eventoId = if (it.oportunidadIdEnEdicion == null && it.eventoId == 0L && listaEventos.isNotEmpty()) {
                                listaEventos.first().id
                            } else {
                                it.eventoId
                            }
                        )
                    }
                }
        }
    }

    private fun cargarOportunidades() {
        viewModelScope.launch {
            oportunidadRepository.obtenerTodas()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar: ${e.message}") }
                }
                .collect { lista ->
                    _estado.update { it.copy(oportunidades = lista) }
                }
        }
    }

    private fun contarPendientes() {
        viewModelScope.launch {
            oportunidadRepository.contarPendientes()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar: ${e.message}") }
                }
                .collect { cantidad ->
                    _estado.update { it.copy(cantPendientes = cantidad) }
                }
        }
    }

    private fun contarPrioridadAlta() {
        viewModelScope.launch {
            oportunidadRepository.contarPrioridadAlta().catch { e ->
                _estado.update { it.copy(mensajeError = "Error al cargar: ${e.message}") }
            }.collect { cantidad -> _estado.update { it.copy(cantPrioridadAlta = cantidad) } }
        }
    }

    private fun contarConfirmadas() {
        viewModelScope.launch {
            oportunidadRepository.contarConfirmadas().catch { e ->
                _estado.update { it.copy(mensajeError = "Error al cargar: ${e.message}") }
            }.collect { cantidad -> _estado.update { it.copy(cantConfirmadas = cantidad) } }
        }
    }

    private fun obtenerOpPrioridadAlta() {
        viewModelScope.launch {
            oportunidadRepository.obtenerOpPrioridadAlta().catch { e ->
                _estado.update { it.copy(mensajeError = "Error al cargar: ${e.message}") }
            }
                .collect { oportunidades -> _estado.update { it.copy(oportunidadesAltas = oportunidades) } }
        }
    }


    fun obtenerOportunidadesPorUsuario(responsable: Long = 0) {

        viewModelScope.launch {
            oportunidadRepository.obtenerOportunidadesPorUsuario(responsable).catch { e ->
                _estado.update { it.copy(mensajeError = "Error al cargar :${e.message}") }
            }.collect { oportunidadesUsuario ->
                _estado.update {
                    it.copy(oportunidadUsuario = oportunidadesUsuario)
                }
            }
        }
    }


    fun onNombreChange(v: String) = _estado.update { it.copy(nombre = v, errorNombre = null) }
    fun onDescripcionChange(v: String) = _estado.update { it.copy(descripcion = v, errorDescripcion = null) }
    fun onFechaDeteccionChange(v: String) = _estado.update { it.copy(fechaDeteccion = v, errorFechaDeteccion = null) }
    fun onPrioridadChange(v: String) = _estado.update {
        it.copy(prioridad = v.filter { ch -> ch.isDigit() }, errorPrioridad = null)
    }
    fun onTipoChange(v: TipoOportunidad) = _estado.update { it.copy(tipo = v) }
    fun onFuenteChange(v: String) = _estado.update { it.copy(fuente = v, errorFuente = null) }

    fun onPotencialChange(v: PotencialComercial) = _estado.update { it.copy(potencial = v) }
    fun onEstadoGestionChange(v: EstadoGestion) = _estado.update { it.copy(estado = v) }
    fun onObservacionesChange(v: String) = _estado.update { it.copy(observaciones = v) }
    fun onEventoIdChange(id: Long) = _estado.update { it.copy(eventoId = id) }
    fun onResponsableChange(id: Long) {
        _estado.update { it.copy(responsable = id) }
    }

    fun prepararEdicion(oportunidad: OportunidadEntity) {
        _estado.update {
            it.copy(
                oportunidadIdEnEdicion = oportunidad.id,
                eventoId = oportunidad.eventoId,
                nombre = oportunidad.nombre,
                tipo = oportunidad.tipo,
                descripcion = oportunidad.descripcion,
                fuente = oportunidad.fuente,
                fechaDeteccion = oportunidad.fechaDeteccion,
                responsable = oportunidad.responsable ?: 0L,
                prioridad = oportunidad.prioridad.toString(),
                potencial = oportunidad.potencial,
                estado = oportunidad.estado,
                fechaActualizacion = oportunidad.fechaActualizacion,
                observaciones = oportunidad.observaciones,
                exitoGuardado = false,
                errorNombre = null,
                errorDescripcion = null,
                errorFechaDeteccion = null,
                errorPrioridad = null,
                errorFuente = null,
                mensajeError = null
            )
        }
    }

    fun cancelarEdicion() {
        limpiarFormulario()
    }

    fun eliminar(oportunidad: OportunidadEntity, onDelete: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                oportunidadRepository.eliminarOportunidad(oportunidad)
                onDelete()
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = "Error al eliminar la oportunidad ${e.message}") }
            }
        }
    }

    fun guardarOportunidad(onExito: () -> Unit = {}) {
        val st = _estado.value

        val errNombre = if (st.nombre.isBlank()) "El nombre de la oportunidad es obligatorio" else null
        val errDesc = if (st.descripcion.isBlank()) "La descripción no puede estar vacía" else null
        val errFecha = if (st.fechaDeteccion.isBlank()) "La fecha de detección es obligatoria" else null
        val prioridadNum = st.prioridad.toIntOrNull()
        val errPrioridad = if (prioridadNum == null || prioridadNum !in 1..5) "Ingresa un nivel entre 1 y 5" else null
        val errFuente = if (st.fuente.isBlank()) "La fuente de deteccion no puede estar vacia" else null

        if (errNombre != null || errDesc != null || errFecha != null || errPrioridad != null || errFuente != null) {
            _estado.update {
                it.copy(
                    errorNombre = errNombre,
                    errorDescripcion = errDesc,
                    errorFechaDeteccion = errFecha,
                    errorPrioridad = errPrioridad,
                    errorFuente = errFuente
                )
            }
            return
        }

        val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val prioridad = st.prioridad.toIntOrNull() ?: 1

        viewModelScope.launch {
            try {
                val oportunidadAProcesar = OportunidadEntity(
                    id = st.oportunidadIdEnEdicion ?: 0,
                    eventoId = st.eventoId,
                    nombre = st.nombre.trim(),
                    tipo = st.tipo,
                    descripcion = st.descripcion.trim(),
                    fuente = st.fuente.trim(),
                    fechaDeteccion = st.fechaDeteccion.trim(),
                    responsable = st.responsable.takeIf { it != 0L },
                    prioridad = prioridad,
                    potencial = st.potencial,
                    estado = st.estado,
                    fechaActualizacion = fechaActual,
                    observaciones = st.observaciones.trim()
                )

                if (st.oportunidadIdEnEdicion != null) {
                    oportunidadRepository.actualizarOportunidad(oportunidadAProcesar)
                } else {
                    oportunidadRepository.insertarOportunidades(listOf(oportunidadAProcesar))
                }
                _estado.update {
                    it.copy(
                        oportunidadIdEnEdicion = null,
                        nombre = "",
                        descripcion = "",
                        fuente = "",
                        fechaDeteccion = "",
                        prioridad = "1",
                        observaciones = "",
                        exitoGuardado = true,
                        errorNombre = null,
                        errorDescripcion = null,
                        errorFechaDeteccion = null,
                        errorPrioridad = null,
                        errorFuente = null,
                        mensajeError = null
                    )
                }

                onExito()
            } catch (e: Exception) {
                _estado.update {
                    it.copy(mensajeError = "Error al guardar la oportunidad: ${e.message}")
                }
            }
        }

    }

    private fun limpiarFormulario() {
        _estado.update {
            it.copy(
                oportunidadIdEnEdicion = null,
                nombre = "",
                descripcion = "",
                fuente = "",
                fechaDeteccion = "",
                observaciones = "",
                errorNombre = null,
                errorDescripcion = null,
                errorPrioridad = null,
                errorFechaDeteccion = null,
                errorFuente = null,
                mensajeError = null
            )
        }
    }

}