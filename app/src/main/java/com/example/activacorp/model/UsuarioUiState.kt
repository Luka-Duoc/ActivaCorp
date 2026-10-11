package com.example.activacorp.model

data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val rol: String = "",
    val errores: UsuarioErrores = UsuarioErrores()
)