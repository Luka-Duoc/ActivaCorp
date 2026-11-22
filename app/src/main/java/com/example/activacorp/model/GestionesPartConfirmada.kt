package com.example.activacorp.model

data class GestionesPartConfirmada(
    val nombre: String,
    val fecha: String,
    val hora: String,
    val tipoDeAccion: TipoDeAccion,
    val estadoFinal: EstadoGestion
)
