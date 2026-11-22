package com.example.activacorp.data.local.seed

import com.example.activacorp.data.local.entity.SeguimientoEntity
import com.example.activacorp.model.TipoDeAccion

object SeguimientoSeed {
    val listaSeguimientos = listOf(
        SeguimientoEntity(
            id = 1L,
            oportunidadRelacionada = 1L,
            usuarioResponsable = 1L,
            tipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
            fecha = "HOY",
            hora = "12:00",
            resultado = null,
            proximaAccion = TipoDeAccion.EN_EVALUACION,
            fechaProxSeguimiento = "DIA",
            observaciones = "Ninguna"
        )
    )
}