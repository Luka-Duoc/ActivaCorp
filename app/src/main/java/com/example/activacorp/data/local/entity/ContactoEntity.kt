package com.example.activacorp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.activacorp.model.enums.AreaContacto
import com.example.activacorp.model.enums.EstadoContacto

@Entity(
    tableName = "contactos",
    foreignKeys = [ForeignKey(
        entity = OrganizacionEntity::class, parentColumns = ["id"],
        childColumns = ["organizacionId"], onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("organizacionId")]
)
data class ContactoEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val organizacionId: Long,
    val nombre: String = "",
    val cargo: String = "",
    val area: AreaContacto,
    val correo: String = "",
    val telefono: String = "",
    val estado: EstadoContacto = EstadoContacto.NUEVO,
    val fechaUltContacto: String? = null,
    val observaciones: String = ""
    )