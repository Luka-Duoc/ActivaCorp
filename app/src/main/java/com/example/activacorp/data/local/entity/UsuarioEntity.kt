package com.example.activacorp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios",
    indices = [Index(value = ["correo"], unique = true)])
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val rol: String = "EJECUTIVO",
    val activo: Boolean = true,
)