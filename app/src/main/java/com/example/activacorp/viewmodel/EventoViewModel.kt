package com.example.activacorp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.local.AppDatabase
import com.example.activacorp.data.local.entity.EventoEntity
import com.example.activacorp.model.EventoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventoViewModel(app: Application): AndroidViewModel(app) {

    private val dao = AppDatabase.getDatabase(app).EventoDao()

    private val _estado = MutableStateFlow(EventoUiState())

    val estado: StateFlow<EventoUiState> = _estado.asStateFlow()

    init {
        cargarEventos()
    }

    private fun cargarEventos() {
        viewModelScope.launch {
            dao.obtenerTodos()
                .catch { e -> _estado.update { it.copy(mensajeError = e.message) } }
                .collect { lista ->
                    _estado.update { it.copy(eventos = lista) }
                }
        }
    }

    fun onNombreChange(v: String) = _estado.update { it.copy(nombre = v, mensajeError = null) }
    fun onTipoChange(v: String) = _estado.update { it.copy(tipo = v) }
    fun onFechaInicioChange(v: String) = _estado.update { it.copy(fechaInicio = v, mensajeError = null) }
    fun onFechaTerminoChange(v: String) = _estado.update { it.copy(fechaTermino = v, mensajeError = null) }
    fun onHorarioChange(v: String) = _estado.update { it.copy(horario = v) }
    fun onDireccionChange(v: String) = _estado.update { it.copy(direccion = v) }
    fun onComunaIdChange(id: Long) = _estado.update { it.copy(comunaId = id) }
    fun onRegionIdChange(id: Long) = _estado.update { it.copy(regionId = id) }
    fun onOrganizacionIdChange(id: Long) = _estado.update { it.copy(organizacionId = id) }
    fun onPublicoEsperadoChange(v: String) = _estado.update { it.copy(publicoEsperado = v) }
    fun onPublicoObjetivoChange(v: String) = _estado.update { it.copy(publicoObjetivo = v) }
    fun onCantAsistentesChange(v: String) = _estado.update { it.copy(cantAsistentes = v.filter { ch -> ch.isDigit() }) }
    fun onCondicionesChange(v: String) = _estado.update { it.copy(condiciones = v) }
    fun onRequisitosChange(v: String) = _estado.update { it.copy(requisitos = v) }
    fun onCostosReferencialesChange(v: String) = _estado.update { it.copy(costosReferenciales = v.filter { ch -> ch.isDigit() }) }
    fun onEspaciosDisponiblesChange(v: String) = _estado.update { it.copy(espaciosDisponibles = v) }
    fun onObservacionesChange(v: String) = _estado.update { it.copy(observaciones = v) }

    fun guardarEvento(onExito: () -> Unit = {}) {
        val st = _estado.value

        if (st.nombre.isBlank() || st.fechaInicio.isBlank() || st.fechaTermino.isBlank()) {
            _estado.update { it.copy(mensajeError = "Nombre y fechas son obligatorios") }
            return
        }

        viewModelScope.launch {
            try {
                dao.insertar(
                    EventoEntity(
                        nombre = st.nombre.trim(),
                        tipo = st.tipo.trim(),
                        fechaInicio = st.fechaInicio.trim(),
                        fechaTermino = st.fechaTermino.trim(),
                        horario = st.horario.trim(),
                        direccion = st.direccion.trim(),
                        comuna = st.comunaId,
                        region = st.regionId,
                        organizacion = st.organizacionId,
                        publicoEsperado = st.publicoEsperado.trim(),
                        publicoObjetivo = st.publicoObjetivo.trim(),
                        cantAsistentes = st.cantAsistentes.toIntOrNull() ?: 0,
                        condiciones = st.condiciones.trim(),
                        requisitos = st.requisitos.trim(),
                        costosReferenciales = st.costosReferenciales.toIntOrNull() ?: 0,
                        espaciosDisponbiles = st.espaciosDisponibles.trim(),
                        observaciones = st.observaciones.trim()
                    )
                )
                _estado.update { EventoUiState(eventos = it.eventos, exitSave = true) }
                onExito()
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = "Error al guardar el evento: ${e.message}") }
            }
        }
    }





}