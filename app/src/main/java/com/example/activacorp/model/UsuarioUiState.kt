package com.example.activacorp.model

data class UsuarioUiState(
    val id: Long = 0,
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val rol: String = "",
    val activo: Boolean = true,
    val errores: UsuarioErrores = UsuarioErrores()
)