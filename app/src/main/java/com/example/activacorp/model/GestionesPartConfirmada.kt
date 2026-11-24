package com.example.activacorp.model

import com.example.activacorp.model.enums.EstadoGestion
import com.example.activacorp.model.enums.TipoDeAccion

data class GestionesPartConfirmada(
    val nombre: String,
    val fecha: String,
    val hora: String,
    val tipoDeAccion: TipoDeAccion,
    val estadoFinal: EstadoGestion
)
