package com.example.activacorp.data.local.seed

import com.example.activacorp.data.local.entity.SeguimientoEntity
import com.example.activacorp.model.enums.TipoDeAccion

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
            id = 3L,
            oportunidadRelacionada = 1L,
            usuarioResponsable = 1L,
            tipoDeAccion = TipoDeAccion.ENVIO_DE_PRESENTACION,
            fecha = "05/11/2026",
            hora = "11:15",
            resultado = 2L,
            proximaAccion = TipoDeAccion.REUNION,
            fechaProxSeguimiento = "10/11/2026",
            observaciones = "Se envió brochure corporativo y detalle de stand modular."
        ),
        SeguimientoEntity(
            id = 4L,
            oportunidadRelacionada = 1L,
            usuarioResponsable = 1L,
            tipoDeAccion = TipoDeAccion.REUNION,
            fecha = "10/11/2026",
            hora = "15:00",
            resultado = 3L,
            proximaAccion = TipoDeAccion.ENVIO_DE_PROPUESTA,
            fechaProxSeguimiento = "14/11/2026",
            observaciones = "Reunión presencial exitosa. Revisarán cotización formal con finanzas esta semana."
        ),
        SeguimientoEntity(
            id = 5L,
            oportunidadRelacionada = 2L,
            usuarioResponsable = 2L,
            tipoDeAccion = TipoDeAccion.INVESTIGACION_INICIAL,
            fecha = "03/11/2026",
            hora = "10:00",
            resultado = null,
            proximaAccion = TipoDeAccion.BUSQUEDA_DE_CONTACTO,
            fechaProxSeguimiento = "06/11/2026",
            observaciones = "Se levantó calendario municipal de actividades para identificar al encargado de área."
        ),
        SeguimientoEntity(
            id = 6L,
            oportunidadRelacionada = 2L,
            usuarioResponsable = 2L,
            tipoDeAccion = TipoDeAccion.BUSQUEDA_DE_CONTACTO,
            fecha = "06/11/2026",
            hora = "16:20",
            resultado = 1L,
            proximaAccion = TipoDeAccion.PRIMER_CONTACTO,
            fechaProxSeguimiento = "09/11/2026",
            observaciones = "Obtenido correo y anexo directo de la coordinación de eventos municipales."
        ),
        SeguimientoEntity(
            id = 7L,
            oportunidadRelacionada = 3L,
            usuarioResponsable = 1L,
            tipoDeAccion = TipoDeAccion.ENVIO_DE_PROPUESTA,
            fecha = "04/11/2026",
            hora = "08:45",
            resultado = 2L,
            proximaAccion = TipoDeAccion.RECEPCION_DE_RESPUESTA,
            fechaProxSeguimiento = "12/11/2026",
            observaciones = "Propuesta enviada para feria de emprendimiento con costos referenciales."
        ),
        SeguimientoEntity(
            id = 8L,
            oportunidadRelacionada = 3L,
            usuarioResponsable = 1L,
            tipoDeAccion = TipoDeAccion.RECEPCION_DE_RESPUESTA,
            fecha = "12/11/2026",
            hora = "11:00",
            resultado = 4L,
            proximaAccion = TipoDeAccion.CONFIRMACION,
            fechaProxSeguimiento = "15/11/2026",
            observaciones = "Confirmaron interés y aprobación preliminar. Solo resta formalizar orden de compra."
        ),
        SeguimientoEntity(
            id = 9L,
            oportunidadRelacionada = 4L,
            usuarioResponsable = 2L,
            tipoDeAccion = TipoDeAccion.SOLICITUD_DE_ANTECEDENTES,
            fecha = "07/11/2026",
            hora = "14:30",
            resultado = 1L,
            proximaAccion = TipoDeAccion.LLAMADA,
            fechaProxSeguimiento = "13/11/2026",
            observaciones = "Se solicitaron planos del recinto, aforo máximo y disponibilidad eléctrica."
        ),
        SeguimientoEntity(
            id = 10L,
            oportunidadRelacionada = 4L,
            usuarioResponsable = 2L,
            tipoDeAccion = TipoDeAccion.LLAMADA,
            fecha = "13/11/2026",
            hora = "17:00",
            resultado = 2L,
            proximaAccion = TipoDeAccion.SEGUIMIENTO,
            fechaProxSeguimiento = "18/11/2026",
            observaciones = "Solicitan ajustar aforo estimado y agregar personal de apoyo adicional."
        ),
        SeguimientoEntity(
            id = 11L,
            oportunidadRelacionada = 5L,
            usuarioResponsable = 1L,
            tipoDeAccion = TipoDeAccion.LLAMADA,
            fecha = "08/11/2026",
            hora = "12:10",
            resultado = null,
            proximaAccion = TipoDeAccion.INFORMACION_INCOMPLETA,
            fechaProxSeguimiento = "11/11/2026",
            observaciones = null
        ),
        SeguimientoEntity(
            id = 12L,
            oportunidadRelacionada = 5L,
            usuarioResponsable = 1L,
            tipoDeAccion = TipoDeAccion.SEGUIMIENTO,
            fecha = "11/11/2026",
            hora = "09:50",
            resultado = 3L,
            proximaAccion = TipoDeAccion.REUNION,
            fechaProxSeguimiento = "19/11/2026",
            observaciones = "Completaron los datos faltantes; interesados en activación para jornada de fin de año."
        ),
        SeguimientoEntity(
            id = 13L,
            oportunidadRelacionada = 6L,
            usuarioResponsable = 2L,
            tipoDeAccion = TipoDeAccion.CONFIRMACION,
            fecha = "14/11/2026",
            hora = "10:30",
            resultado = 4L,
            proximaAccion = TipoDeAccion.ACTIVACION_REALIZADA,
            fechaProxSeguimiento = "21/11/2026",
            observaciones = "Acordados los requisitos de acreditación previa para expositores y montaje."
        ),
        SeguimientoEntity(
            id = 14L,
            oportunidadRelacionada = 7L,
            usuarioResponsable = 1L,
            tipoDeAccion = TipoDeAccion.ENVIO_DE_PROPUESTA,
            fecha = "15/11/2026",
            hora = "16:00",
            resultado = 1L,
            proximaAccion = TipoDeAccion.EN_EVALUACION,
            fechaProxSeguimiento = "20/11/2026",
            observaciones = "Propuesta recibida por comité interno; definirán presupuesto esta semana."
        ),
        SeguimientoEntity(
            id = 15L,
            oportunidadRelacionada = 8L,
            usuarioResponsable = 2L,
            tipoDeAccion = TipoDeAccion.SEGUIMIENTO,
            fecha = "16/11/2026",
            hora = "11:40",
            resultado = 2L,
            proximaAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
            fechaProxSeguimiento = "24/11/2026",
            observaciones = "Cliente evalúa dos fechas tentativas en diciembre; retomar contacto."
        ),
        SeguimientoEntity(
            id = 16L,
            oportunidadRelacionada = 8L,
            usuarioResponsable = 2L,
            tipoDeAccion = TipoDeAccion.CIERRE,
            fecha = "18/11/2026",
            hora = "15:25",
            resultado = 4L,
            proximaAccion = TipoDeAccion.OPORTUNIDAD_CERRADA,
            fechaProxSeguimiento = null,
            observaciones = "Proceso cerrado y documentado en su totalidad."
        )
    )
}