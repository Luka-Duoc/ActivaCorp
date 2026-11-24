package com.example.activacorp.data.local.seed

import com.example.activacorp.data.local.entity.ContactoEntity
import com.example.activacorp.model.enums.AreaContacto
import com.example.activacorp.model.enums.EstadoContacto

object ContactosSeed {
    val listaContactos = listOf(
        ContactoEntity(
            id = 1L,
            organizacionId = 1L,
            nombre = "Camila Valdés",
            cargo = "Jefa de Bienestar y Personas",
            area = AreaContacto.MARKETING,
            correo = "c.valdes@andina.example.com",
            telefono = "+56 9 8123 4501",
            estado = EstadoContacto.CONTACTADO,
            fechaUltContacto = "15/10/2026",
            observaciones = "Solicitó catálogo de beneficios corporativos para fin de año."
        ),
        ContactoEntity(
            id = 2L,
            organizacionId = 1L,
            nombre = "Rodrigo Muñoz",
            cargo = "Coordinador de Marketing",
            area = AreaContacto.MARKETING,
            correo = "r.munoz@andina.example.com",
            telefono = "+56 9 8123 4502",
            estado = EstadoContacto.NUEVO,
            fechaUltContacto = null,
            observaciones = "Contacto secundario para activaciones en tiendas."
        ),
        ContactoEntity(
            id = 3L,
            organizacionId = 2L,
            nombre = "Patricia Soto",
            cargo = "Encargada de DIDECO",
            area = AreaContacto.COMUNICACIONES,
            correo = "psoto@villaesperanza.example.com",
            telefono = "+56 9 7234 5603",
            estado = EstadoContacto.SIN_RESPUESTA,
            fechaUltContacto = "18/10/2026",
            observaciones = "Coordina calendario de operativos sociales y ferias vecinales."
        ),
        ContactoEntity(
            id = 4L,
            organizacionId = 3L,
            nombre = "Andrés Contreras",
            cargo = "Jefe de Relaciones Institucionales",
            area = AreaContacto.ADMINISTRACION,
            correo = "acontreras@desarrolloregional.example.com",
            telefono = "+56 9 6345 6704",
            estado = EstadoContacto.CONTACTADO,
            fechaUltContacto = "20/10/2026",
            observaciones = "Pidió propuesta formal con 15 días de anticipación."
        ),
        ContactoEntity(
            id = 5L,
            organizacionId = 4L,
            nombre = "Marcela Pizarro",
            cargo = "Directora Administrativa",
            area = AreaContacto.ADMINISTRACION,
            correo = "mpizarro@horizonte.example.com",
            telefono = "+56 9 5456 7805",
            estado = EstadoContacto.NUEVO,
            fechaUltContacto = null,
            observaciones = "Evaluando convenios para docentes y asistentes de la educación."
        ),
        ContactoEntity(
            id = 6L,
            organizacionId = 5L,
            nombre = "Felipe Araya",
            cargo = "Productor Ejecutivo",
            area = AreaContacto.COMUNICACIONES,
            correo = "faraya@prisma.example.com",
            telefono = "+56 9 9567 8906",
            estado = EstadoContacto.SIN_RESPUESTA,
            fechaUltContacto = "22/10/2026",
            observaciones = "Dispone de espacios para stands en congresos de noviembre y diciembre."
        ),
        ContactoEntity(
            id = 7L,
            organizacionId = 6L,
            nombre = "Daniela Rojas",
            cargo = "Ejecutiva Comercial de Espacios",
            area = AreaContacto.ADMINISTRACION,
            correo = "drojas@portaldelvalle.example.com",
            telefono = "+56 9 8678 9007",
            estado = EstadoContacto.CONTACTADO,
            fechaUltContacto = "25/10/2026",
            observaciones = "Envió tarifario de módulos promocionales en pasillo central."
        ),
        ContactoEntity(
            id = 8L,
            organizacionId = 7L,
            nombre = "Hernán Silva",
            cargo = "Gerente Gremial",
            area = AreaContacto.ADMINISTRACION,
            correo = "hsilva@empresasdelsur.example.com",
            telefono = "+56 9 7789 0108",
            estado = EstadoContacto.NUEVO,
            fechaUltContacto = null,
            observaciones = "Interesado en difundir servicios entre empresas socias."
        ),
        ContactoEntity(
            id = 9L,
            organizacionId = 8L,
            nombre = "Valentina Herrera",
            cargo = "Coordinadora de Alianzas",
            area = AreaContacto.COMUNICACIONES,
            correo = "vherrera@nuevoshorizontes.example.com",
            telefono = "+56 9 6890 1209",
            estado = EstadoContacto.CONTACTADO,
            fechaUltContacto = "28/10/2026",
            observaciones = "Busca colaboradores para jornada comunitaria de cierre de año."
        )
    )
}