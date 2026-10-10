package com.example.activacorp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.local.UsuarioDatabase
import com.example.activacorp.data.local.UsuarioEntity
import com.example.activacorp.model.UsuarioErrores
import com.example.activacorp.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(app: Application) : AndroidViewModel(app) {


    private val dao = UsuarioDatabase.getDatabase(app).UsuarioDao()
    private val _estado = MutableStateFlow(UsuarioUiState())

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

        val errores = UsuarioErrores(
            nombre = if(estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if(!estadoActual.correo.contains("@gmail")) "Correo invalido" else null,
            clave = if(estadoActual.clave.length < 6) "La clave debe tener al menos 6 caracteres" else null
        )

        val findError = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !findError

    }

    fun guardar(onListo: () -> Unit) {
        val a = _estado.value
        viewModelScope.launch {
            dao.insertar(
                UsuarioEntity(
                    nombre = a.nombre,
                    correo = a.correo,
                    clave = a.clave
                )
            )
            onListo()
        }
    }
}