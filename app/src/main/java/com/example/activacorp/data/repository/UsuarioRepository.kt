package com.example.activacorp.data.repository

import com.example.activacorp.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {

    fun obtenerUsuarios(): Flow<List<UsuarioEntity>>
    suspend fun insertarUsuario(usuario: UsuarioEntity)
    suspend fun actualizarUsuario(usuario: UsuarioEntity)
    suspend fun eliminarUsuario(usuario: UsuarioEntity)
    suspend fun buscarCorreo(correo: String): UsuarioEntity?
    suspend fun login(correo: String, clave: String): UsuarioEntity?
    suspend fun limpiarSesion()

    suspend fun eliminarEjecutivo(usuario: UsuarioEntity)
}