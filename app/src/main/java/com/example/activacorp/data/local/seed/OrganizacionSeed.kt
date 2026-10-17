package com.example.activacorp.data.local.seed

import com.example.activacorp.data.local.entity.OrganizacionEntity
import com.example.activacorp.model.TipoOrganizacion

object OrganizacionSeed {
    val listaOrganizaciones = listOf(
        OrganizacionEntity(
            nombre = "Comercial Andina SpA",
            tipo = TipoOrganizacion.EMPRESA,
            rubro = "Comercio y retail",
            comuna = 1L,
            sitioWeb = "https://andina.example.com",
            publico = "Trabajadores y sus familias",
            historial = "Interesada en convenios corporativos y jornadas de beneficios."
        ),
        OrganizacionEntity(
            nombre = "Municipalidad de Villa Esperanza",
            tipo = TipoOrganizacion.MUNICIPALIDAD,
            rubro = "Administración municipal",
            comuna = 2L,
            sitioWeb = "https://villaesperanza.example.com",
            publico = "Vecinos, funcionarios municipales y familias",
            historial = "Organiza ferias de servicios y actividades comunitarias."
        ),
        OrganizacionEntity(
            nombre = "Instituto Público de Desarrollo Regional",
            tipo = TipoOrganizacion.INSTITUCION_PUBLICA,
            rubro = "Servicios públicos",
            comuna = 3L,
            sitioWeb = "https://desarrolloregional.example.com",
            publico = "Funcionarios públicos y ciudadanía",
            historial = "Realiza encuentros institucionales y jornadas informativas."
        ),
        OrganizacionEntity(
            nombre = "Centro Educativo Horizonte",
            tipo = TipoOrganizacion.INSTITUCION_PRIVADA,
            rubro = "Educación",
            comuna = 4L,
            sitioWeb = "https://horizonte.example.com",
            publico = "Docentes, personal administrativo y familias",
            historial = "Evalúa incorporar beneficios para su comunidad educativa."
        ),
        OrganizacionEntity(
            nombre = "Eventos Prisma",
            tipo = TipoOrganizacion.ORGANIZADOR_EVENTOS,
            rubro = "Producción y organización de eventos",
            comuna = 5L,
            sitioWeb = "https://prisma.example.com",
            publico = "Asistentes a ferias, congresos y eventos empresariales",
            historial = "Gestiona eventos con espacios potenciales para activaciones."
        ),
        OrganizacionEntity(
            nombre = "Portal del Valle",
            tipo = TipoOrganizacion.CENTRO_COMERCIAL,
            rubro = "Comercio y entretenimiento",
            comuna = 6L,
            sitioWeb = "https://portaldelvalle.example.com",
            publico = "Visitantes, familias y consumidores",
            historial = "Evalúa habilitar espacios para stands y campañas promocionales."
        ),
        OrganizacionEntity(
            nombre = "Asociación de Empresas del Sur",
            tipo = TipoOrganizacion.ASOCIACION,
            rubro = "Asociatividad empresarial",
            comuna = 7L,
            sitioWeb = "https://empresasdelsur.example.com",
            publico = "Empresas asociadas y representantes comerciales",
            historial = "Promueve encuentros empresariales y actividades de colaboración."
        ),
        OrganizacionEntity(
            nombre = "Fundación Nuevos Horizontes",
            tipo = TipoOrganizacion.OTROS,
            rubro = "Desarrollo social",
            comuna = 8L,
            sitioWeb = "https://nuevoshorizontes.example.com",
            publico = "Beneficiarios, voluntarios y organizaciones colaboradoras",
            historial = "Participa en jornadas comunitarias y actividades de vinculación."
        )
    )
}