package com.example.activacorp.data.local.seed
import com.example.activacorp.data.local.entity.ComunaEntity

object ComunaSeed {
    val listaComunas = listOf(
        ComunaEntity(id = 1L, nombre = "Puente Alto", idRegion = 1L),
        ComunaEntity(id = 2L, nombre = "La Florida", idRegion = 1L),
        ComunaEntity(id = 3L, nombre = "Santiago Centro", idRegion = 1L),
        ComunaEntity(id = 4L, nombre = "Providencia", idRegion = 1L),

        ComunaEntity(id = 5L, nombre = "Valparaíso", idRegion = 2L),
        ComunaEntity(id = 6L, nombre = "Viña del Mar", idRegion = 2L),

        ComunaEntity(id = 7L, nombre = "Concepción", idRegion = 3L),
        ComunaEntity(id = 8L, nombre = "Talcahuano", idRegion = 3L)
    )
}