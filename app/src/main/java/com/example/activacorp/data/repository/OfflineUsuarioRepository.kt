package com.example.activacorp.data.repository

import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.data.local.dao.UsuarioDao
import com.example.activacorp.data.local.entity.UsuarioEntity

class OfflineUsuarioRepository(private val usuarioDao: UsuarioDao) : UsuarioRepository {
    override suspend fun insertarUsuario(usuario: UsuarioEntity) = usuarioDao.insertar(usuario)
    override suspend fun actualizarUsuario(usuario: UsuarioEntity) = usuarioDao.actualizar(usuario)
    override suspend fun limpiarSesion() { usuarioDao.cerrarSesion() }
    override suspend fun buscarCorreo(correo: String): UsuarioEntity? { return usuarioDao.buscarCorreo(correo) }
}