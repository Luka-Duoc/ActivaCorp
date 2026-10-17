package com.example.activacorp.data.local.seed

import com.example.activacorp.data.local.entity.RegionEntity

object RegionSeed {
    val listaRegiones = listOf(
        RegionEntity(id = 1L, nombre = "Región Metropolitana de Santiago"),
        RegionEntity(id = 2L, nombre = "Región de Valparaíso"),
        RegionEntity(id = 3L, nombre = "Región del Biobío")
    )
}