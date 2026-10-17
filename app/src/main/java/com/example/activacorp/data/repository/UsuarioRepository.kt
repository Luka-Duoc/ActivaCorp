package com.example.activacorp.data.repository

import com.example.activacorp.data.local.entity.UsuarioEntity

interface UsuarioRepository {
    suspend fun insertarUsuario(usuario: UsuarioEntity)
    suspend fun actualizarUsuario(usuario: UsuarioEntity)
    suspend fun limpiarSesion()
    suspend fun buscarCorreo(correo: String): UsuarioEntity?
}