package com.example.activacorp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.local.entity.OrganizacionEntity
import com.example.activacorp.data.repository.ComunaRepository
import com.example.activacorp.data.repository.OrganizacionRepository
import com.example.activacorp.model.OrganizacionUiState
import com.example.activacorp.model.TipoOrganizacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrganizacionViewModel(
    private val organizacionRepository: OrganizacionRepository,
    private val comunaRepository: ComunaRepository
) : ViewModel() {

    private val _estado = MutableStateFlow(OrganizacionUiState())
    val estado: StateFlow<OrganizacionUiState> = _estado.asStateFlow()

    init {
        cargarOrganizaciones()
        cargarComunas()
    }

    fun cargarOrganizaciones() {
        viewModelScope.launch {
            organizacionRepository.obtenerTodas()
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
            comunaRepository.obtenerTodas()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar las comunas: ${e.message}") }
                }
                .collect { lista ->
                    _estado.update {
                        it.copy(
                            comunas = lista,
                            comunaId = if (it.idEnEdicion == null && it.comunaId == 0L && lista.isNotEmpty()) {
                                lista.first().id
                            } else {
                                it.comunaId
                            }
                        )
                    }
                }
        }
    }

    fun eliminarOrganizacion(organizacion: OrganizacionEntity) {
        viewModelScope.launch {
            try {
                organizacionRepository.eliminar(organizacion)
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = "Error al eliminar: ${e.message}") }
            }
        }
    }

    fun onNombreChange(valor: String) = _estado.update { it.copy(nombre = valor, errorNombre = null) }
    fun onTipoChange(valor: TipoOrganizacion) = _estado.update { it.copy(tipo = valor) }
    fun onRubroChange(valor: String) = _estado.update { it.copy(rubro = valor, errorRubro = null) }
    fun onComunaChange(id: Long) = _estado.update { it.copy(comunaId = id) }
    fun onSitioWebChange(valor: String) = _estado.update { it.copy(sitioWeb = valor) }
    fun onPublicoChange(valor: String) = _estado.update { it.copy(publico = valor, errorPublico = null) }
    fun onHistorialChange(valor: String) = _estado.update { it.copy(historial = valor, errorHistorial = null) }


    fun prepararEdicion(organizacion: OrganizacionEntity) {
        _estado.update {
            it.copy(
                idEnEdicion = organizacion.id,
                nombre = organizacion.nombre,
                tipo = organizacion.tipo,
                rubro = organizacion.rubro,
                comunaId = organizacion.comuna,
                sitioWeb = organizacion.sitioWeb ?: "",
                publico = organizacion.publico,
                historial = organizacion.historial,
                errorNombre = null,
                errorRubro = null,
                errorPublico = null,
                errorHistorial = null,
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
                sitioWeb = "",
                publico = "",
                historial = "",
                errorNombre = null,
                errorRubro = null,
                errorPublico = null,
                errorHistorial = null,
                mensajeError = null
            )
        }
    }

    fun guardarOrganizacion(onExito: () -> Unit) {
        val st = _estado.value

        val errNombre = if (st.nombre.isBlank()) "El nombre no puede estar vacio" else null
        val errRubro = if (st.rubro.isBlank()) "El rubro no puede estar vacio" else null
        val errPublico = if (st.publico.isBlank()) "El publico no puede estar vacio" else null
        val errHistorial = if (st.historial.isBlank()) "El historial no puede estar vacio" else null

        if (errNombre != null || errRubro != null || errPublico != null || errHistorial != null) {
            _estado.update {
                it.copy(
                    errorNombre = errNombre,
                    errorRubro = errRubro,
                    errorPublico = errPublico,
                    errorHistorial = errHistorial
                )
            }
            return
        }

        if (st.comunaId == 0L) {
            _estado.update { it.copy(mensajeError = "Debe seleccionar una comuna valida") }
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
            historial = st.historial.trim()
        )

        viewModelScope.launch {
            try {
                if (st.idEnEdicion != null) {
                    organizacionRepository.actualizar(entidad)
                } else {
                    organizacionRepository.insertar(listOf(entidad))
                }
                resetFormulario()
                onExito()
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = "Error al guardar: ${e.message}") }
            }
        }

    }
}