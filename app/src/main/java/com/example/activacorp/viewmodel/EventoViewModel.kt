package com.example.activacorp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.local.entity.EventoEntity
import com.example.activacorp.data.repository.ComunaRepository
import com.example.activacorp.data.repository.EventoRepository
import com.example.activacorp.data.repository.OrganizacionRepository
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
import androidx.compose.ui.text.input.TextFieldValue

class EventoViewModel(
    private val eventoRepository: EventoRepository,
    private val comunaRepository: ComunaRepository,
    private val organizacionRepository: OrganizacionRepository
): ViewModel() {

    private val _estado = MutableStateFlow(EventoUiState())

    val estado: StateFlow<EventoUiState> = _estado.asStateFlow()

    init {
        cargarEventos()
        cargarComuna()
        cargarOrganizacion()
    }

    fun cargarOrganizacion() {
        viewModelScope.launch {
            organizacionRepository.obtenerTodas()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar las organizaciones: ${e.message}") }
                }
                .collect { lista ->
                    _estado.update { it.copy(organizaciones = lista) }
                }
        }
    }

    private fun cargarComuna() {
        viewModelScope.launch {
            comunaRepository.obtenerTodas().collect { lista ->
                _estado.update { actual ->
                    actual.copy(
                        comunas = lista,
                        comuna = if (actual.eventoIdEnEdicion == null && actual.comuna == 0L) {
                            lista.firstOrNull()?.id ?: 1L
                        } else {
                            actual.comuna
                        },
                        comunaSeleccionada = if (actual.eventoIdEnEdicion == null && actual.comunaSeleccionada.isBlank()) {
                            lista.firstOrNull()?.nombre ?: ""
                        } else {
                            actual.comunaSeleccionada
                        }
                    )
                }
            }
        }
    }

    fun validarHora(entrada: String): String? {
        // Deja solo los dígitos
        val digitos = entrada.filter { it.isDigit() }

        if (digitos.length != 8) return null

        val horaInicio = digitos.substring(0, 2).toIntOrNull() ?: return null
        val minInicio = digitos.substring(2, 4).toIntOrNull() ?: return null
        val horaFin = digitos.substring(4, 6).toIntOrNull() ?: return null
        val minFin = digitos.substring(6, 8).toIntOrNull() ?: return null

        // Valida rango de horas (00-23) y minutos (00-59)
        if (horaInicio!in 0..23 || minInicio !in 0..59) return null
        if (horaFin !in 0..23 || minFin !in 0..59) return null

        // Valida que la hora de término sea mayor a la de inicio
        val totalMinInicio = horaInicio * 60 + minInicio
        val totalMinFin = horaFin * 60 + minFin
        if (totalMinFin <= totalMinInicio) return null

        return String.format(Locale.getDefault(),
            "%02d:%02d - %02d:%02d",
            horaInicio, minInicio,
                    horaFin, minFin
            )
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
            eventoRepository.obtenerTodos()
                .catch { e -> _estado.update { it.copy(mensajeError = e.message) } }
                .collect { lista ->
                    _estado.update { it.copy(eventos = lista) }
                }
        }
    }

    fun onNombreChange(v: String) = _estado.update { it.copy(nombre = v, errorNombre = null) }
    fun onTipoChange(v: String) = _estado.update { it.copy(tipo = v) }
    fun onFechaInicioChange(v: String) =
        _estado.update { it.copy(fechaInicio = v, errorFecha = null) }

    fun onFechaTerminoChange(v: String) =
        _estado.update { it.copy(fechaTermino = v, errorFecha = null) }

    fun onDireccionChange(v: String) = _estado.update { it.copy(direccion = v, errorDireccion = null) }
    fun onCantAsistentesChange(v: String) =
        _estado.update { it.copy(cantAsistentes = v.filter { ch -> ch.isDigit() }) }

    fun onCostosReferencialesChange(v: String) =
        _estado.update { it.copy(costosReferenciales = v.filter { ch -> ch.isDigit() }) }

    fun onRequisitosChange(v: String) = _estado.update { it.copy(requisitos = v, errorRequisitos = null) }
    fun onObservacionesChange(v: String) = _estado.update { it.copy(observaciones = v) }

    fun onOrganizacionChange(id: Long) {
        _estado.update { it.copy(organizacion = id) }
    }

    fun onHorarioChange(input: TextFieldValue) {

        val digitos = input.text.filter { it.isDigit() }.take(8)

        val formateado = buildString {
            digitos.forEachIndexed { index, c ->
                append(c)
                if (index == 1 && digitos.length > 2) append(":")
                if (index == 3 && digitos.length > 4) append(" - ")
                if (index == 5 && digitos.length > 6) append(":")
            }
        }

        _estado.update {
            it.copy(
                horario = formateado,
                errorHora = null
            )
        }
    }

    fun eliminarEvento(evento: EventoEntity, onExito: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                eventoRepository.eliminar(evento)
                onExito()
            } catch (e: Exception) {
                _estado.update { it.copy(mensajeError = "Error al eliminar el evento: ${e.message}") }
            }
        }
    }

    fun editar(evento: EventoEntity) {
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
                errorNombre = null,
                errorHora = null,
                errorFecha = null,
                errorDireccion = null,
                errorRequisitos = null,
                mensajeError = null
            )
        }
    }

    // Limpiar Formulario si el usuario cancela la edicion
    fun cancelarEdicion() {
        limpiarFormulario()
    }

    // Guardar y editar
    fun guardarEvento(onExito: () -> Unit = {}) {
        val st = _estado.value

        val errNombre = if (st.nombre.isBlank()) "El nombre no puede estar vacío" else null

        val fechaIni = validacionFecha(st.fechaInicio)
        val fechaFin = validacionFecha(st.fechaTermino)
        val errFecha = when {
            st.fechaInicio.isBlank() || st.fechaTermino.isBlank() -> "Las fechas son obligatorias"
            fechaIni == null || fechaFin == null -> "Formato de fecha inválido (dd/MM/yyyy)"
            fechaFin.before(fechaIni) -> "La fecha de término no puede ser anterior a la de inicio"
            else -> null
        }

        val horarioValido = validarHora(st.horario)
        val errHora = when {
            st.horario.isBlank() -> "La hora no puede estar vacía"
            horarioValido == null -> "Horario inválido (ej: 17:50 - 22:00)"
            else -> null
        }

        val errDireccion = if (st.direccion.isBlank()) "La dirección es obligatoria" else null
        val errRequisitos = if (st.requisitos.isBlank()) "Los requisitos no pueden estar en blanco" else null

        if (errNombre != null || errFecha != null || errHora != null || errDireccion != null || errRequisitos != null) {
            _estado.update {
                it.copy(
                    errorNombre = errNombre,
                    errorFecha = errFecha,
                    errorHora = errHora,
                    errorDireccion = errDireccion,
                    errorRequisitos = errRequisitos
                )
            }
            return
        }

        viewModelScope.launch {
            try {
                val eventoAProcesar = EventoEntity(
                    id = st.eventoIdEnEdicion ?: 0, // Si está editando usa el ID existente, si es nuevo usa 0
                    nombre = st.nombre.trim(),
                    tipo = st.tipo.trim(),
                    fechaInicio = st.fechaInicio.trim(),
                    fechaTermino = st.fechaTermino.trim(),
                    horario = horarioValido!!,
                    direccion = st.direccion.trim(),
                    comuna = st.comuna,
                    organizacion = st.organizacion,
                    cantAsistentes = st.cantAsistentes.toIntOrNull() ?: 0,
                    requisitos = st.requisitos.trim(),
                    costosReferenciales = st.costosReferenciales.toIntOrNull() ?: 0,
                    observaciones = st.observaciones.trim()
                )
                if (st.eventoIdEnEdicion != null) {
                    eventoRepository.actualizar(eventoAProcesar)
                } else {
                    eventoRepository.insertar(eventoAProcesar)
                }

                // Resetea el formulario tras guardar o actualizar
                limpiarFormulario(exitSave = true)
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
                eventoRepository.insertar(evento)
                onResultado(true, "¡Evento '${evento.nombre}' registrado con éxito!")
            } catch (e: Exception) {
                onResultado(false, "Error al guardar el evento: ${e.message}")
            }
        }
    }
    private fun limpiarFormulario(exitSave: Boolean = false) {
        _estado.update { actual ->
            actual.copy(
                // 1. Conservar las listas existentes en memoria
                organizaciones = actual.organizaciones,
                eventos = actual.eventos,
                comunas = actual.comunas,

                // 2. Limpiar solo los campos del formulario
                eventoIdEnEdicion = null,
                nombre = "",
                tipo = "",
                fechaInicio = "",
                fechaTermino = "",
                horario = "",
                direccion = "",
                comuna = actual.comunas.firstOrNull()?.id ?: 1L,
                comunaSeleccionada = actual.comunas.firstOrNull()?.nombre ?: "",
                organizacion = 0L,
                organizacionId = 0L,
                cantAsistentes = "",
                costosReferenciales = "",
                requisitos = "",
                observaciones = "",
                mensajeError = null,
                errorNombre = null,
                errorHora = null,
                errorFecha = null,
                errorDireccion = null,
                errorRequisitos = null,
                exitSave = exitSave
            )
        }
    }
}