package com.example.activacorp.data.local

class OfflineUsuarioRepository(private val usuarioDao: UsuarioDao) : UsuarioRepository {
    override suspend fun insertarUsuario(usuario: UsuarioEntity) = usuarioDao.insertar(usuario)
    override suspend fun actualizarUsuario(usuario: UsuarioEntity) = usuarioDao.actualizar(usuario)

}