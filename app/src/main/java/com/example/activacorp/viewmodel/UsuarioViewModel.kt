package com.example.activacorp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.local.AppDatabase
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.data.repository.OfflineUsuarioRepository
import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.model.RolUsuario
import com.example.activacorp.model.UsuarioErrores
import com.example.activacorp.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getDatabase(app).UsuarioDao()
    private val _estado = MutableStateFlow(UsuarioUiState())
    private val usuarioRepository: UsuarioRepository = OfflineUsuarioRepository(dao)
    val estado: StateFlow<UsuarioUiState> = _estado

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
                    rol = rol.titulo
                )
            )
            _estado.update { UsuarioUiState() }
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
            val usuarioEncontrado = dao.login(correoActual, claveActual)
            if (usuarioEncontrado != null) {
                _estado.update {
                    it.copy(
                        nombre = usuarioEncontrado.nombre,
                        correo = usuarioEncontrado.correo,
                        rol = usuarioEncontrado.rol,
                        errores = UsuarioErrores()
                    )
                }
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
            _estado.value = UsuarioUiState()
        }
    }


}