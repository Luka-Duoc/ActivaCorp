package com.example.activacorp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.local.entity.ContactoEntity
import com.example.activacorp.data.local.entity.OrganizacionEntity
import com.example.activacorp.data.repository.ContactoRepository
import com.example.activacorp.data.repository.OrganizacionRepository
import com.example.activacorp.model.AreaContacto
import com.example.activacorp.model.ContactoUiState
import com.example.activacorp.model.EstadoContacto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactoViewModel(
    private val contactoRepository: ContactoRepository,
    private val organizacionRepository: OrganizacionRepository
): ViewModel() {

    private val  _estado = MutableStateFlow(ContactoUiState())
    val estado: StateFlow<ContactoUiState> = _estado.asStateFlow()

    init {
        cargarContactos()
        cargarOrganizaciones()
    }

    private fun cargarContactos() {
        viewModelScope.launch {
            contactoRepository.obtenerTodos()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar los contactos") }
                }
                .collect { lista -> _estado.update { it.copy(contactos = lista) }}
        }
    }

    private fun cargarOrganizaciones() {
        viewModelScope.launch {
            organizacionRepository.obtenerTodas()
                .catch { e ->
                    _estado.update { it.copy(mensajeError = "Error al cargar las organizaciones") }
                }
                .collect { lista -> _estado.update { it.copy(organizaciones = lista) }}
        }
    }

    fun contactosPorOrganizacion(organizacionId: Long): Flow<List<ContactoEntity>> =
        contactoRepository.obtenerPorOrganizacion(organizacionId)

    fun onNombreChange(v: String) =
        _estado.update { it.copy(nombre = v, errorNombre = null, mensajeError = null) }

    fun onCargoChange(v: String) =
        _estado.update { it.copy(cargo = v, errorCargo = null, mensajeError = null) }

    fun onAreaChange(v: AreaContacto) = _estado.update { it.copy(area = v) }

    fun onCorreoChange(v: String) =
        _estado.update { it.copy(correo = v, ErrorContacto = null, mensajeError = null) }

    fun onTelefonoChange(v: String) =
        _estado.update { it.copy(telefono = v, ErrorContacto = null, mensajeError = null) }

    fun onEstadoChange(v: EstadoContacto) = _estado.update { it.copy(estado = v) }

    fun onObservacionesChange(v: String) = _estado.update { it.copy(observaciones = v) }

    fun onOrganizacionSeleccionada(org: OrganizacionEntity) =
        _estado.update {
            it.copy(
                organizacionId = org.id,
                organizacionSeleccionada = org.nombre,
                ErrorOrganizacion = null
            )
        }

    fun nuevoContacto(organizacionId: Long = 0L) {
        _estado.update { actual ->
            ContactoUiState(
                contactos = actual.contactos,
                organizaciones = actual.organizaciones,
                organizacionId = organizacionId,
                organizacionSeleccionada = actual.organizaciones
                    .firstOrNull { it.id == organizacionId }?.nombre ?: ""
            )
        }
    }

    fun cargarParaEditar(c: ContactoEntity) {
        _estado.update {
            it.copy(
                contactoIdEdicion = c.id,
                nombre = c.nombre,
                cargo = c.cargo,
                area = c.area,
                correo = c.correo,
                telefono = c.telefono,
                estado = c.estado,
                observaciones = c.observaciones,
                organizacionId = c.organizacionId,
                organizacionSeleccionada = it.organizaciones
                    .firstOrNull { o -> o.id == c.organizacionId }?.nombre ?: "",
                errorNombre = null,
                errorCargo = null,
                ErrorOrganizacion = null,
                ErrorContacto = null,
                mensajeError = null,
                exitoSave = false
            )
        }
    }

    private fun validarFormulario(): Boolean {
        val e = _estado.value
        val correo = e.correo.trim()
        val telefono = e.telefono.trim()
        val digitos = telefono.filter { it.isDigit() }

        val errorNombre = if (e.nombre.trim().length < 3) "Ingresa un nombre (mínimo 3 caracteres)" else null
        val errorCargo = if (e.cargo.isBlank()) "Campo obligatorio" else null
        val errorOrganizacion = if (e.organizacionId == 0L) "Selecciona una organización" else null
        val errorContacto = when {
            correo.isBlank() && telefono.isBlank() -> "Ingresa un correo o un teléfono"
            correo.isNotBlank() && (!correo.contains("@")) -> "Correo no válido"
            telefono.isNotBlank() && digitos.length !in 8..11 -> "Teléfono no válido"
            else -> null
        }

        _estado.update {
            it.copy(
                errorNombre = errorNombre,
                errorCargo = errorCargo,
                ErrorOrganizacion = errorOrganizacion,
                ErrorContacto = errorContacto
            )
        }
        return listOf(errorNombre, errorCargo, errorOrganizacion, errorContacto).all { it == null }
    }

    fun guardar() {
        if (!validarFormulario()) return
        val e = _estado.value

        viewModelScope.launch {
            try {
                val idEdicion = e.contactoIdEdicion
                if (idEdicion == null) {
                    contactoRepository.insertar(
                        ContactoEntity(
                            organizacionId = e.organizacionId,
                            nombre = e.nombre.trim(),
                            cargo = e.cargo.trim(),
                            area = e.area,
                            correo = e.correo.trim(),
                            telefono = e.telefono.trim(),
                            estado = e.estado,
                            observaciones = e.observaciones.trim()
                        )
                    )
                } else {

                    val original = e.contactos.firstOrNull { it.id == idEdicion }
                    if (original == null) {
                        _estado.update { it.copy(mensajeError = "El contacto ya no existe") }
                        return@launch
                    }
                    contactoRepository.actualizar(
                        original.copy(
                            organizacionId = e.organizacionId,
                            nombre = e.nombre.trim(),
                            cargo = e.cargo.trim(),
                            area = e.area,
                            correo = e.correo.trim(),
                            telefono = e.telefono.trim(),
                            estado = e.estado,
                            observaciones = e.observaciones.trim()
                        )
                    )
                }
                _estado.update { it.copy(exitoSave = true) }
            } catch (ex: Exception) {
                _estado.update { it.copy(mensajeError = "Error al guardar el contacto: ${ex.message}") }
            }
        }
    }

    fun eliminar(contacto: ContactoEntity) {
        viewModelScope.launch {
            try {
                contactoRepository.eliminar(contacto)
            } catch (ex: Exception) {
                _estado.update { it.copy(mensajeError = "Error al eliminar el contacto") }
            }
        }
    }

}