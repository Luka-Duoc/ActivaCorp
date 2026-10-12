package com.example.activacorp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "organizaciones")
data class OrganizacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String = ""
)
