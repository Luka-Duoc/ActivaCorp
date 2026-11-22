package com.example.activacorp.data.local.seed

import com.example.activacorp.data.local.entity.SeguimientoEntity
import com.example.activacorp.model.TipoDeAccion

object SeguimientoSeed {
    val listaSeguimientos = listOf(
        SeguimientoEntity(
            oportunidadRelacionada = 1L,
            usuarioResponsable = 1L,
            tipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
            fecha = "HOY",
            hora = "12:00",
            resultado = 1L,
            proximaAccion = TipoDeAccion.EN_EVALUACION,
            fechaProxSeguimiento = "DIA",
            observaciones = "Ninguna"
        ),
        SeguimientoEntity(
            oportunidadRelacionada = 2L,
            usuarioResponsable = 2L,
            tipoDeAccion = TipoDeAccion.PRIMER_CONTACTO,
            fecha = "02/11/2026",
            hora = "09:30",
            resultado = null,
            proximaAccion = TipoDeAccion.ENVIO_DE_PRESENTACION,
            fechaProxSeguimiento = "05/11/2026",
            observaciones = "Primer contacto con gerencia de personas. Solicitan presentación formal de servicios."
        ),
    )
}