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
            fecha = "20/10/2026",
            hora = "12:00",
            resultado = 1L,
            proximaAccion = TipoDeAccion.EN_EVALUACION,
            fechaProxSeguimiento = "25/10/2026",
            observaciones = "Evaluación inicial tras participación en evento."
        ),
        SeguimientoEntity(
            id = 2L,
            oportunidadRelacionada = 2L,
            usuarioResponsable = 2L,
            tipoDeAccion = TipoDeAccion.PRIMER_CONTACTO,
            fecha = "02/11/2026",
            hora = "09:30",
            resultado = 2L,
            proximaAccion = TipoDeAccion.ENVIO_DE_PRESENTACION,
            fechaProxSeguimiento = "05/11/2026",
            observaciones = "Primer contacto con gerencia de personas. Solicitan presentación formal de servicios."
        ),
        SeguimientoEntity(
            id = 3L,
            oportunidadRelacionada = 3L,
            usuarioResponsable = 3L,
            tipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
            fecha = "06/11/2026",
            hora = "11:15",
            resultado = 3L,
            proximaAccion = TipoDeAccion.EN_EVALUACION,
            fechaProxSeguimiento = "10/11/2026",
            observaciones = "Pendiente validación de presupuesto anual de bienestar."
        ),
        SeguimientoEntity(
            id = 4L,
            oportunidadRelacionada = 1L,
            usuarioResponsable = 1L,
            tipoDeAccion = TipoDeAccion.ENVIO_DE_PRESENTACION,
            fecha = "12/11/2026",
            hora = "15:00",
            resultado = 4L,
            proximaAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
            fechaProxSeguimiento = "16/11/2026",
            observaciones = "Se envió propuesta de pausas activas y operativo preventivo para 120 colaboradores."
        ),
        SeguimientoEntity(
            id = 5L,
            oportunidadRelacionada = 2L,
            usuarioResponsable = 2L,
            tipoDeAccion = TipoDeAccion.EN_EVALUACION,
            fecha = "17/11/2026",
            hora = "10:45",
            resultado = 5L,
            proximaAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
            fechaProxSeguimiento = "21/11/2026",
            observaciones = "Comité de RRHH revisando alcance de talleres ergonómicos y fechas de ejecución."
        ),
        SeguimientoEntity(
            id = 6L,
            oportunidadRelacionada = 3L,
            usuarioResponsable = 1L,
            tipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
            fecha = "20/11/2026",
            hora = "16:30",
            resultado = 6L,
            proximaAccion = TipoDeAccion.ENVIO_DE_PRESENTACION,
            fechaProxSeguimiento = "24/11/2026",
            observaciones = "Cliente solicita ajuste en cantidad de jornadas y cotización final."
        )
    )
}