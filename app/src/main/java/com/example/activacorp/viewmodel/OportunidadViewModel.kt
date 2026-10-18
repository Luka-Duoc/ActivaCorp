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

class OportunidadViewModel(app: Application): AndroidViewModel(app) {

    private val dao = AppDatabase.getDatabase(app).OportunidadDao()

    private val _estado = MutableStateFlow(OportunidadUiState())
    val estado: StateFlow<OportunidadUiState> = _estado.asStateFlow()

    init {
        cargarOportunidades()
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


    fun onNombreChange(v: String) = _estado.update { it.copy(nombre = v, mensajeError = null) }
    fun onTipoChange(v: TipoOportunidad) = _estado.update { it.copy(tipo = v) }
    fun onDescripcionChange(v: String) = _estado.update { it.copy(descripcion = v) }
    fun onFuenteChange(v: String) = _estado.update { it.copy(fuente = v) }
    fun onFechaDeteccionChange(v: String) = _estado.update { it.copy(fechaDeteccion = v, mensajeError = null) }
    fun onResponsableChange(id: Long) = _estado.update { it.copy(responsable = id) }
    fun onPrioridadChange(v: String) = _estado.update { it.copy(prioridad = v.filter { ch -> ch.isDigit() }) }
    fun onPotencialChange(v: PotencialComercial) = _estado.update { it.copy(potencial = v) }
    fun onEstadoGestionChange(v: EstadoGestion) = _estado.update { it.copy(estado = v) }
    fun onObservacionesChange(v: String) = _estado.update { it.copy(observaciones = v) }

    fun prepararEdicion(oportunidad: OportunidadEntity) {
        _estado.update {
            it.copy(
                oportunidadIdEnEdicion = oportunidad.id,
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
            } catch (e: Exception) {
                _estado.update {
                    it.copy(mensajeError = "Error al guardar la oportunidad: ${e.message}")
                }
            }
        }

    }

}