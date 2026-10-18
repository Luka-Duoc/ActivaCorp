package com.example.activacorp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.local.AppDatabase
import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.local.entity.EventoEntity
import com.example.activacorp.model.EventoQr
import com.example.activacorp.model.EventoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventoViewModel(app: Application): AndroidViewModel(app) {

    private val dao = AppDatabase.getDatabase(app).EventoDao()

    private val _estado = MutableStateFlow(EventoUiState())

    val estado: StateFlow<EventoUiState> = _estado.asStateFlow()
    private val comunaDao = AppDatabase.getDatabase(app).ComunaDao()
    private val organizacionDao = AppDatabase.getDatabase(app).OrganizacionDao()

    init {
        cargarEventos()
        cargarComuna()
        cargarOrganizacion()
    }

    fun cargarOrganizacion() {
        viewModelScope.launch {
            organizacionDao.obtenerTodas()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar las organizaciones") }
                }
                .collect { lista ->
                    _estado.update { it.copy(organizaciones = lista) }
                }
        }
    }

    private fun cargarComuna() {
        viewModelScope.launch {
            comunaDao.obtenerTodas().collect { lista ->
                _estado.update { actual ->
                    actual.copy(
                        comunas = lista,
                        // Asigna la primera si no hay ninguna seleccionada
                        comuna = if (actual.comuna == 0L) lista.firstOrNull()?.id ?: 1L else actual.comuna,
                        comunaSeleccionada = if (actual.comunaSeleccionada.isBlank()) lista.firstOrNull()?.nombre ?: "" else actual.comunaSeleccionada
                    )
                }
            }
        }
    }

    fun validarHora(entrada: String): String? {
        // Deja solo los dígitos
        val digitos = entrada.filter { it.isDigit() }

        if (digitos.length != 4) return null

        val hora = digitos.substring(0, 2).toIntOrNull() ?: return null
        val minutos = digitos.substring(2, 4).toIntOrNull() ?: return null

        // Valida rango de horas (00-23) y minutos (00-59)
        if (hora !in 0..23 || minutos !in 0..59) return null

        return String.format(Locale.getDefault(), "%02d:%02d", hora, minutos)
    }

    fun validacionFecha(fechaStr: String): Date? {
        return try {
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
                isLenient = false // Evita interpretar fechas invalidas
            }
            formato.parse(fechaStr)
        } catch (e: Exception) {
            null
        }
    }

    fun onComunaSeleccionada(comuna: ComunaEntity) {
        _estado.update {
            it.copy(
                comuna = comuna.id,
                comunaSeleccionada = comuna.nombre
            )
        }
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
    fun onFechaInicioChange(v: String) =
        _estado.update { it.copy(fechaInicio = v, mensajeError = null) }

    fun onFechaTerminoChange(v: String) =
        _estado.update { it.copy(fechaTermino = v, mensajeError = null) }
    fun onOrganizacionSeleccionada(id: Long) {
        _estado.update { it.copy(organizacionId = id) }
    }

    fun onDireccionChange(v: String) = _estado.update { it.copy(direccion = v) }
    fun onComunaChange(id: Long) = _estado.update { it.copy(comuna = id) }
    fun onOrganizacionChange(id: Long) = _estado.update { it.copy(organizacion = id) }
    fun onCantAsistentesChange(v: String) =
        _estado.update { it.copy(cantAsistentes = v.filter { ch -> ch.isDigit() }) }

    fun onCostosReferencialesChange(v: String) =
        _estado.update { it.copy(costosReferenciales = v.filter { ch -> ch.isDigit() }) }

    fun onRequisitosChange(v: String) = _estado.update { it.copy(requisitos = v) }
    fun onObservacionesChange(v: String) = _estado.update { it.copy(observaciones = v) }

    fun onHorarioChange(v: String) {
        val filtrado = v.filter { it.isDigit() || it == ':' }
        if (filtrado.length <= 5) { // Limita la longitud máxima a "00:00"
            _estado.update { it.copy(horario = filtrado) }
        }
    }

    fun eliminarEvento(evento: EventoEntity, onExito: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                dao.eliminar(evento)
                onExito()
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = "Error al eliminar el evento: ${e.message}") }
            }
        }
    }

    fun editar(evento: EventoEntity) {
        cargarOrganizacion()
        cargarEventos()
        val nombreDeComuna = _estado.value.comunas.firstOrNull { it.id == evento.comuna }?.nombre ?: ""
        _estado.update {
            it.copy(
                eventoIdEnEdicion = evento.id,
                nombre = evento.nombre,
                tipo = evento.tipo,
                fechaInicio = evento.fechaInicio,
                fechaTermino = evento.fechaTermino,
                horario = evento.horario,
                direccion = evento.direccion,
                comuna = evento.comuna,
                comunaSeleccionada = nombreDeComuna,
                organizacion = evento.organizacion,
                cantAsistentes = evento.cantAsistentes.toString(),
                costosReferenciales = evento.costosReferenciales.toString(),
                requisitos = evento.requisitos,
                observaciones = evento.observaciones,
                mensajeError = null
            )
        }
    }

    // Limpiar Formulario si el usuario cancela la edicion
    fun cancelarEdicion() {
        cargarOrganizacion()
        cargarEventos()
        _estado.update {
            EventoUiState(
                eventos = it.eventos,
                comunas = it.comunas,
                comuna = it.comunas.firstOrNull()?.id ?: 1L,
                comunaSeleccionada = it.comunas.firstOrNull()?.nombre ?: ""
            )
        }
    }

    // Guardar y editar
    fun guardarEvento(onExito: () -> Unit = {}) {
        val st = _estado.value

        if (st.nombre.isBlank() || st.fechaInicio.isBlank() || st.fechaTermino.isBlank()) {
            _estado.update { it.copy(mensajeError = "Nombre y fechas son obligatorios") }
            return
        }

        if (st.direccion.isBlank()) {
            _estado.update { it.copy(mensajeError = "Direccion es obligatorio") }
            return
        }

        val fechaIni = validacionFecha(st.fechaInicio)
        val fechaFin = validacionFecha(st.fechaTermino)
        if (fechaIni == null || fechaFin == null) {
            _estado.update { it.copy(mensajeError = "Formato de fecha inválido") }
            return
        }

        if (fechaFin.before(fechaIni)) {
            _estado.update { it.copy(mensajeError = "La fecha de término no puede ser anterior a la de inicio") }
            return
        }

        val horarioFinal = if (st.horario.isNotBlank()) {
            val horarioValido = validarHora(st.horario)
            if (horarioValido == null) {
                _estado.update { it.copy(mensajeError = "El horario debe ser válido (ej: 1750 o 17:50)") }
                return
            }
            horarioValido
        } else {
            ""
        }

        viewModelScope.launch {
            try {
                val eventoAProcesar = EventoEntity(
                    id = st.eventoIdEnEdicion ?: 0, // Si está editando usa el ID existente, si es nuevo usa 0
                    nombre = st.nombre.trim(),
                    tipo = st.tipo.trim(),
                    fechaInicio = st.fechaInicio.trim(),
                    fechaTermino = st.fechaTermino.trim(),
                    horario = horarioFinal,
                    direccion = st.direccion.trim(),
                    comuna = st.comuna,
                    organizacion = st.organizacion,
                    cantAsistentes = st.cantAsistentes.toIntOrNull() ?: 0,
                    requisitos = st.requisitos.trim(),
                    costosReferenciales = st.costosReferenciales.toIntOrNull() ?: 0,
                    observaciones = st.observaciones.trim()
                )
                if (st.eventoIdEnEdicion != null) {
                    dao.actualizar(eventoAProcesar)
                } else {
                    dao.insertar(eventoAProcesar)
                }

                // Resetea el formulario tras guardar o actualizar
                _estado.update { actual ->
                    EventoUiState(
                        eventos = actual.eventos,
                        comunas = actual.comunas,
                        comuna = actual.comunas.firstOrNull()?.id ?: 1L,
                        comunaSeleccionada = actual.comunas.firstOrNull()?.nombre ?: "",
                        exitSave = true
                    )
                }
                cargarEventos()
                cargarOrganizacion()
                onExito()
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = "Error al procesar el evento: ${e.message}") }
            }
        }
    }

    fun registrarEscaneoQR(qrContenido: String, onResultado: (Boolean, String) -> Unit) {
        val evento = EventoQr.escanear(qrContenido)

        if (evento == null) {
            onResultado(false, "El código QR no tiene el formato de evento válido.")
            return
        }

        if (evento.nombre.isBlank() || evento.fechaInicio.isBlank() || evento.fechaTermino.isBlank()) {
            onResultado(false, "El evento del QR no contiene campos obligatorios.")
            return
        }

        viewModelScope.launch {
            try {
                dao.insertar(evento)
                onResultado(true, "¡Evento '${evento.nombre}' registrado con éxito!")
            } catch (e: Exception) {
                onResultado(false, "Error al guardar el evento: ${e.message}")
            }
        }
        cargarOrganizacion()
        cargarEventos()
    }
}