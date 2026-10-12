package com.example.activacorp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: UsuarioEntity)

    @Update
    suspend fun actualizar(usuario: UsuarioEntity)

    @Delete
    suspend fun eliminar(usuario: UsuarioEntity)

    @Query("select * from usuarios")
    fun obtenerUsuarios(): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM usuarios ORDER BY id DESC LIMIT 1")
    fun obtenerUltimo(): Flow<UsuarioEntity?>

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave LIMIT 1")
    suspend fun login(correo: String, clave: String): UsuarioEntity?
}