package com.example.activacorp.model

import com.example.activacorp.data.local.entity.UsuarioEntity

data class UsuarioUiState(
    val id: Long = 0,
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val rol: String = "",
    val activo: Boolean = true,
    val usuarios: List<UsuarioEntity> = emptyList(),
    val errores: UsuarioErrores = UsuarioErrores()
)