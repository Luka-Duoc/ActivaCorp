package com.example.activacorp.data.local

import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {
    suspend fun insertarUsuario(usuario: UsuarioEntity)
    suspend fun actualizarUsuario(usuario: UsuarioEntity)
}