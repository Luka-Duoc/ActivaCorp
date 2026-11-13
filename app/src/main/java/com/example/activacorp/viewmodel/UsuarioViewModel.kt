package com.example.activacorp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.local.SessionDataStore
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.model.RolUsuario
import com.example.activacorp.model.UsuarioErrores
import com.example.activacorp.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val usuarioRepository: UsuarioRepository,
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUiState())

    val estado: StateFlow<UsuarioUiState> = _estado

    private val _sesionVerificada = MutableStateFlow(false)
    val sesionVerificada: StateFlow<Boolean> = _sesionVerificada

    init {
        cargarUsuarios()
        restaurarSesion()
    }

    private fun restaurarSesion() {
        viewModelScope.launch {
            sessionDataStore.sesionUsuario.collect { usuarioGuardado ->
                if (usuarioGuardado != null) {
                    _estado.update {
                        it.copy(
                            id = usuarioGuardado.id,
                            nombre = usuarioGuardado.nombre,
                            correo = usuarioGuardado.correo,
                            clave = usuarioGuardado.clave,
                            rol = usuarioGuardado.rol
                        )
                    }
                }
                _sesionVerificada.value = true
            }
        }
    }

    private fun cargarUsuarios() {
        viewModelScope.launch {
            usuarioRepository.obtenerUsuarios()
                .collect { lista ->
                    _estado.update { it.copy(usuarios = lista) }
                }
        }
    }

    fun eliminarEjecutivo(usuario: UsuarioEntity) {
        viewModelScope.launch {
            try {
                usuarioRepository.eliminarEjecutivo(usuario)
            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        errores = it.errores.copy(nombre = "Error al eliminar: ${e.message}")
                    )
                }
            }
        }
    }

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun validarForm(): Boolean {
        val estadoActual = _estado.value
        val correoActual = estadoActual.correo.trim()
        val rol = RolUsuario.asignarCorreo(correoActual)

        val errores = UsuarioErrores(
            nombre = if(estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (correoActual.isBlank() || rol == null) "Correo no valido" else null,
            clave = if(estadoActual.clave.length < 6) "La clave debe tener al menos 6 caracteres" else null
        )

        val findError = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !findError

    }

    fun guardar(onListo: () -> Unit) {
        if(!validarForm()) return

        val a = _estado.value
        val correo = a.correo.trim()
        val rol = RolUsuario.asignarCorreo(correo) ?: RolUsuario.EJECUTIVO

        viewModelScope.launch {
            val usuarioExistente = usuarioRepository.buscarCorreo(correo)
            if (usuarioExistente != null) {
                _estado.update {
                    it.copy(errores = it.errores.copy(correo = "Este correo ya esta registrado"))
                }
                return@launch
            }
            usuarioRepository.insertarUsuario(
                UsuarioEntity(
                    nombre = a.nombre.trim(),
                    correo = correo,
                    clave = a.clave,
                    rol = rol.name
                )
            )
            _estado.update { UsuarioUiState(usuarios = it.usuarios) }
            onListo()
        }
    }

    fun iniciarSesion(onLoginSuccess: () -> Unit) {
        val correoActual = _estado.value.correo.trim()
        val claveActual = _estado.value.clave.trim()

        if (correoActual.isBlank() || claveActual.isBlank()) {
            _estado.update {
                it.copy(errores = it.errores.copy(clave = "Ingresa correo y contraseña"))
            }
            return
        }

        viewModelScope.launch {
            val usuarioEncontrado = usuarioRepository.login(correoActual, claveActual)
            if (usuarioEncontrado != null) {
                _estado.update {
                    it.copy(
                        id = usuarioEncontrado.id,
                        nombre = usuarioEncontrado.nombre,
                        correo = usuarioEncontrado.correo,
                        clave = usuarioEncontrado.clave,
                        rol = usuarioEncontrado.rol,
                        errores = UsuarioErrores()
                    )
                }
                sessionDataStore.guardarSesion(usuarioEncontrado)
                onLoginSuccess()
            } else {
                _estado.update {
                    it.copy(
                        errores = it.errores.copy(clave = "Correo o contrasela incorrectos")
                    )
                }
            }
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            usuarioRepository.limpiarSesion()
            sessionDataStore.limpiarSesion()
            _estado.update { UsuarioUiState(usuarios = it.usuarios) }
        }
    }

    fun eliminar(usuario: UsuarioEntity, onEliminar: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                usuarioRepository.eliminarUsuario(usuario)
                sessionDataStore.limpiarSesion() // <-- Agrega esta línea
                _estado.update { UsuarioUiState(usuarios = it.usuarios) }
                onEliminar()
            } catch (e: Exception) {
                _estado.update {
                    it.copy(
                        errores = it.errores.copy(nombre = "Error al eliminar al usuario: ${e.message}")
                    )
                }
            }
        }
    }


}