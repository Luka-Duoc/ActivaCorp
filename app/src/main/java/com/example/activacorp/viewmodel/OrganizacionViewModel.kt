package com.example.activacorp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.activacorp.data.local.AppDatabase
import com.example.activacorp.data.local.dao.ComunaDao
import com.example.activacorp.data.local.entity.OrganizacionEntity
import com.example.activacorp.model.OrganizacionUiState
import com.example.activacorp.model.TipoOrganizacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrganizacionViewModel(app: Application) : AndroidViewModel(app) {
    private val db = AppDatabase.getDatabase(app)
    private val dao = db.OrganizacionDao()
    private val ComunaDao = db.ComunaDao()
    private val eventoDao = db.EventoDao()

    private val _estado = MutableStateFlow(OrganizacionUiState())
    val estado: StateFlow<OrganizacionUiState> = _estado.asStateFlow()

    init {
        cargarOrganizaciones()
        cargarComunas()
        cargarEventos()
    }

    fun cargarOrganizaciones() {
        viewModelScope.launch {
            dao.obtenerTodas()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar las organizaciones") }
                }
                .collect { lista ->
                    _estado.update { it.copy(organizaciones = lista) }
                }
        }
    }

    fun cargarComunas() {
        viewModelScope.launch {
            ComunaDao.obtenerTodas()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar las comunas") }
                }
                .collect { lista ->
                    _estado.update {
                        it.copy(
                            comunas = lista,
                            comunaId = if (it.comunaId == 0L && lista.isNotEmpty()) lista.first().id else it.comunaId
                        )
                    }
                }
        }
    }

    fun cargarEventos() {
        viewModelScope.launch {
            eventoDao.obtenerTodos()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar los eventos: ${e.message}") }
                }
                .collect { lista ->
                    _estado.update {
                        it.copy(
                            listaEventos = lista,
                            eventoId = if (it.eventoId == 0L && lista.isNotEmpty()) lista.first().id else it.eventoId
                        )
                    }
                }
        }
    }

    fun eliminarOrganizacion(organizacion: OrganizacionEntity) {
        viewModelScope.launch {
            try {
                dao.eliminar(organizacion)
                cargarOrganizaciones() // Recarga la lista en la UI
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = "Error al eliminar: ${e.message}") }
            }
        }
    }

    fun onNombreChange(valor: String) = _estado.update { it.copy(nombre = valor, mensajeError = null) }
    fun onTipoChange(valor: TipoOrganizacion) = _estado.update { it.copy(tipo = valor) }
    fun onRubroChange(valor: String) = _estado.update { it.copy(rubro = valor) }
    fun onComunaChange(id: Long) = _estado.update { it.copy(comunaId = id) }
    fun onEventoChange(id: Long) = _estado.update { it.copy(eventoId = id) }
    fun onSitioWebChange(valor: String) = _estado.update { it.copy(sitioWeb = valor) }
    fun onPublicoChange(valor: String) = _estado.update { it.copy(publico = valor) }
    fun onHistorialChange(valor: String) = _estado.update { it.copy(historial = valor) }


    fun prepararEdicion(organizacion: OrganizacionEntity) {
        cargarComunas()
        cargarEventos()
        _estado.update {
            it.copy(
                idEnEdicion = organizacion.id,
                nombre = organizacion.nombre,
                tipo = organizacion.tipo,
                rubro = organizacion.rubro,
                comunaId = organizacion.comuna,
                eventoId = organizacion.eventoId,
                sitioWeb = organizacion.sitioWeb ?: "",
                publico = organizacion.publico,
                historial = organizacion.historial,
                mensajeError = null
            )
        }
    }

    fun resetFormulario() {
        _estado.update {
            it.copy(
                idEnEdicion = null,
                nombre = "",
                tipo = TipoOrganizacion.EMPRESA,
                rubro = "",
                comunaId = it.comunas.firstOrNull()?.id ?: 0L,
                eventoId = it.listaEventos.firstOrNull()?.id ?: 0L,
                sitioWeb = "",
                publico = "",
                historial = "",
                mensajeError = null
            )
        }
    }

    fun guardarOrganizacion(onExito: () -> Unit) {
        val st = _estado.value

        if (st.nombre.isBlank()) {
            _estado.update { it.copy(mensajeError = "El nombre no puede estar en blanco") }
            return
        }

        if (st.rubro.isBlank() || st.publico.isBlank()) {
            _estado.update { it.copy(mensajeError = "El rubro y el publico no puede estar en blanco") }
            return
        }

        if (st.historial.length < 3) {
            _estado.update { it.copy(mensajeError = "El historial debe tener al menos 3 letras") }
            return
        }

        if (st.comunaId == 0L) {
            _estado.update { it.copy(mensajeError = "Debe seleccionar una comuna valida") }
            return
        }

        if (st.eventoId == 0L) {
            _estado.update { it.copy(mensajeError = "Debe seleccionar un evento asociado") }
            return
        }

        val entidad = OrganizacionEntity(
            id = st.idEnEdicion ?: 0L,
            nombre = st.nombre.trim(),
            tipo = st.tipo,
            rubro = st.rubro.trim(),
            comuna = st.comunaId,
            sitioWeb = st.sitioWeb?.trim()?.ifEmpty { null },
            publico = st.publico.trim(),
            historial = st.historial.trim(),
            eventoId = st.eventoId
        )

        viewModelScope.launch {
            try {
                if (st.idEnEdicion != null) {
                    dao.actualizar(entidad)
                } else {
                    dao.insertar(listOf(entidad))
                }
                resetFormulario()
                cargarOrganizaciones()
                cargarComunas()
                cargarEventos()
                onExito()
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = "Error al guardar: ${e.message}") }
            }
        }

    }
}