package com.example.activacorp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.local.AppDatabase
import com.example.activacorp.data.local.entity.OportunidadEntity
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

class OportunidadViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getDatabase(app).OportunidadDao()
    private val db = AppDatabase.getDatabase(app)
    private val eventoDao = db.EventoDao()
    private val usuarioDao = db.UsuarioDao()
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
            usuarioDao.obtenerUsuarios()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar usuarios: ${e.message}") }
                }
                .collect { lista ->
                    _estado.update {
                        it.copy(
                            usuariosDisponibles = lista,
                            // Si aún no se ha seleccionado ninguno, selecciona el primero por defecto
                            responsable = if (it.responsable == 0L && lista.isNotEmpty()) lista.first().id else it.responsable
                        )
                    }
                }
        }
    }

    private fun cargarEventos() {
        viewModelScope.launch {
            eventoDao.obtenerTodos()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar eventos: ${e.message}") }
                }
                .collect { listaEventos ->
                    _estado.update {
                        it.copy(
                            eventosDisponibles = listaEventos,
                            eventoId = if (it.eventoId == 0L && listaEventos.isNotEmpty()) listaEventos.first().id else it.eventoId
                        )
                    }
                }
        }
    }

    private fun cargarOportunidades() {
        viewModelScope.launch {
            dao.obtenerTodas()
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
            dao.contarPendientes().catch { e ->
                _estado.update { it.copy(mensajeError = "Error al cargar: ${e.message}") }
            }.collect { cantidad -> _estado.update { it.copy(cantPendientes = cantidad) } }
        }
    }

    private fun contarPrioridadAlta() {
        viewModelScope.launch {
            dao.contarPrioridadAlta().catch { e ->
                _estado.update { it.copy(mensajeError = "Error al cargar: ${e.message}") }
            }.collect { cantidad -> _estado.update { it.copy(cantPrioridadAlta = cantidad) } }
        }
    }

    private fun contarConfirmadas() {
        viewModelScope.launch {
            dao.contarConfirmadas().catch { e ->
                _estado.update { it.copy(mensajeError = "Error al cargar: ${e.message}") }
            }.collect { cantidad -> _estado.update { it.copy(cantConfirmadas = cantidad) } }
        }
    }

    private fun obtenerOpPrioridadAlta() {
        viewModelScope.launch {
            dao.obtenerOpPrioridadAlta().catch { e ->
                _estado.update { it.copy(mensajeError = "Error al cargar: ${e.message}") }
            }
                .collect { oportunidades -> _estado.update { it.copy(oportunidadesAltas = oportunidades) } }
        }
    }


    fun obtenerOportunidadesPorUsuario(responsable: Long = 0) {

        viewModelScope.launch {
            dao.obtenerOportunidadesPorUsuario(responsable).catch { e ->
                _estado.update { it.copy(mensajeError = "Error al cargar :${e.message}") }
            }.collect { oportunidadesUsuario ->
                _estado.update {
                    it.copy(oportunidadUsuario = oportunidadesUsuario)
                }
            }
        }
    }


    fun onNombreChange(v: String) = _estado.update { it.copy(nombre = v, mensajeError = null) }
    fun onTipoChange(v: TipoOportunidad) = _estado.update { it.copy(tipo = v) }
    fun onDescripcionChange(v: String) = _estado.update { it.copy(descripcion = v) }
    fun onFuenteChange(v: String) = _estado.update { it.copy(fuente = v) }
    fun onFechaDeteccionChange(v: String) =
        _estado.update { it.copy(fechaDeteccion = v, mensajeError = null) }

    fun onPrioridadChange(v: String) =
        _estado.update { it.copy(prioridad = v.filter { ch -> ch.isDigit() }) }

    fun onPotencialChange(v: PotencialComercial) = _estado.update { it.copy(potencial = v) }
    fun onEstadoGestionChange(v: EstadoGestion) = _estado.update { it.copy(estado = v) }
    fun onObservacionesChange(v: String) = _estado.update { it.copy(observaciones = v) }
    fun onEventoIdChange(id: Long) = _estado.update { it.copy(eventoId = id) }
    fun onResponsableChange(id: Long) {
        _estado.update { it.copy(responsable = id) }
    }

    fun prepararEdicion(oportunidad: OportunidadEntity) {
        cargarEventos()
        cargarUsuarios()
        _estado.update {
            it.copy(
                oportunidadIdEnEdicion = oportunidad.id,
                eventoId = oportunidad.eventoId,
                nombre = oportunidad.nombre,
                tipo = oportunidad.tipo,
                descripcion = oportunidad.descripcion,
                fuente = oportunidad.fuente,
                fechaDeteccion = oportunidad.fechaDeteccion,
                responsable = oportunidad.responsable,
                prioridad = oportunidad.prioridad.toString(),
                potencial = oportunidad.potencial,
                estado = oportunidad.estado,
                fechaActualizacion = oportunidad.fechaActualizacion,
                observaciones = oportunidad.observaciones,
                mensajeError = null
            )
        }
    }

    fun cancelarEdicion() {
        _estado.update {
            OportunidadUiState(oportunidades = it.oportunidades)
        }
    }

    fun eliminar(oportunidad: OportunidadEntity, onDelete: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                dao.eliminar(oportunidad)
                onDelete()
                cargarOportunidades()
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = "Error al eliminar la oportunidad") }
            }
        }
    }

    fun guardarOportunidad(onExito: () -> Unit = {}) {
        val st = _estado.value

        if (st.nombre.isBlank() || st.descripcion.isBlank()) {
            _estado.update { it.copy(mensajeError = "El nombre y/o la descripcion de la oportunidad no puede estar en blanco") }
            return
        }

        if (st.fechaDeteccion.isBlank()) {
            _estado.update { it.copy(mensajeError = "La fecha de deteccion es obligatoria") }
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
                    responsable = st.responsable,
                    prioridad = prioridad,
                    potencial = st.potencial,
                    estado = st.estado,
                    fechaActualizacion = fechaActual,
                    observaciones = st.observaciones.trim()
                )

                if (st.oportunidadIdEnEdicion != null) {
                    dao.actualizar(oportunidadAProcesar)
                } else {
                    dao.insertar(listOf(oportunidadAProcesar))
                }
                _estado.update {
                    OportunidadUiState(
                        oportunidades = it.oportunidades,
                        exitoGuardado = true
                    )
                }
                onExito()
                cargarEventos()
                cargarUsuarios()
            } catch (e: Exception) {
                _estado.update {
                    it.copy(mensajeError = "Error al guardar la oportunidad: ${e.message}")
                }
            }
        }

    }

}