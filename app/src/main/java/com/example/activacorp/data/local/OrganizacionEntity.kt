package com.example.activacorp.data.local

import androidx.room.Entity

@Entity(tableName = "organizaciones")
data class OrganizacionEntity(
    val id: Long = 0,
    val nombre: String = ""
)
