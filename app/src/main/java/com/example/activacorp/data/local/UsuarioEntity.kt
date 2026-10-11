package com.example.activacorp.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val rol: String = "EJECUTIVO",
    val activo: Boolean = true,
)