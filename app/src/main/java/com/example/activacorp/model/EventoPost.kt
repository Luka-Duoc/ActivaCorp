package com.example.activacorp.model

import com.google.gson.annotations.SerializedName

data class EventoPost(
    @SerializedName("_id") // Mapea el id generado por mongo
    val id: String = "",
    val nombre: String = "",
    val tipo: String = "",
    val horario: String = "",
    val fechaInicio: String = "",
    val fechaTermino: String = "",
    val direccion: String = "",
    val comuna: Long = 0L,
    val asistentes: Int = 0,
    val costosEstimados: Int = 0,
    val requisitos: String = "",
    val observaciones: String = "",
    val organizacion: Long = 1L
)
