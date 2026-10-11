package com.example.activacorp.model

data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val errores: UsuarioErrores = UsuarioErrores(),
    val errorLogin: String? = null
)