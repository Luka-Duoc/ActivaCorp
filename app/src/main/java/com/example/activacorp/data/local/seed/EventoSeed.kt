package com.example.activacorp.data.local.seed

import com.example.activacorp.data.local.entity.EventoEntity

object EventoSeed {
    val listaEvento = listOf (
        EventoEntity(
            id = 1L,
            nombre = "Expo Bienestar y Salud Laboral 2026",
            tipo = "Feria Empresarial",
            fechaInicio = "10/11/2026",
            fechaTermino = "12/11/2026",
            horario = "09:00",
            direccion = "Av. Las Condes 12450",
            comuna = 1L,
            organizacion = 1L,
            cantAsistentes = 450,
            requisitos = "Stands modulares, credencial de expositor y acreditación previa.",
            costosReferenciales = 350000,
            observaciones = "Requiere confirmación de espacio para presentación antes del 30 de octubre."
        ),
        EventoEntity(
            id = 2L,
            nombre = "Jornada Deportiva y Recreativa ActivaCorp",
            tipo = "Actividad de Trabajadores",
            fechaInicio = "20/11/2026",
            fechaTermino = "20/11/2026",
            horario = "10:00",
            direccion = "Parque O'Higgins s/n",
            comuna = 2L,
            organizacion = 2L,
            cantAsistentes = 200,
            requisitos = "Indumentaria deportiva, hidratación provista por organizadores.",
            costosReferenciales = 150000,
            observaciones = "Punto de contacto coordinado con recursos humanos."
        ),
        EventoEntity(
            id = 3L,
            nombre = "Congreso Anual de Gestión de Personas",
            tipo = "Evento Corporativo",
            fechaInicio = "05/12/2026",
            fechaTermino = "06/12/2026",
            horario = "08:30",
            direccion = "Av. Santa María 2050",
            comuna = 3L,
            organizacion = 3L,
            cantAsistentes = 600,
            requisitos = "Inscripción en plataforma del congreso y pago de matrícula institucional.",
            costosReferenciales = 500000,
            observaciones = "Oportunidad de patrocinio en etapa de cierre."
        ),
        EventoEntity(
            id = 4L,
            nombre = "Feria de Innovación y Beneficios Educativos",
            tipo = "Feria Institucional",
            fechaInicio = "10/12/2026",
            fechaTermino = "11/12/2026",
            horario = "09:30",
            direccion = "Av. Apoquindo 4500",
            comuna = 4L,
            organizacion = 4L,
            cantAsistentes = 300,
            requisitos = "Módulo informativo, material gráfico institucional y acreditación docente.",
            costosReferenciales = 220000,
            observaciones = "Dirigido a docentes, funcionarios administrativos y familias."
        ),
        EventoEntity(
            id = 5L,
            nombre = "Cumbre Empresarial de Proveedores y Servicios",
            tipo = "Feria Empresarial",
            fechaInicio = "14/12/2026",
            fechaTermino = "15/12/2026",
            horario = "10:00",
            direccion = "Centro de Eventos Riesco 5330",
            comuna = 5L,
            organizacion = 5L,
            cantAsistentes = 750,
            requisitos = "Reserva de stand estándar, conexión eléctrica y pase de montaje.",
            costosReferenciales = 650000,
            observaciones = "Incluye rueda de negocios con gerentes comerciales."
        ),
        EventoEntity(
            id = 6L,
            nombre = "Activación Promocional Fin de Año",
            tipo = "Activación Comercial",
            fechaInicio = "18/12/2026",
            fechaTermino = "20/12/2026",
            horario = "11:00",
            direccion = "Av. Américo Vespucio 1501",
            comuna = 6L,
            organizacion = 6L,
            cantAsistentes = 1200,
            requisitos = "Permiso de activación en pasillo central y gráfica autoportante.",
            costosReferenciales = 480000,
            observaciones = "Alta afluencia de público familiar durante el fin de semana."
        ),
        EventoEntity(
            id = 7L,
            nombre = "Encuentro Regional de Alianzas Empresariales",
            tipo = "Evento Corporativo",
            fechaInicio = "22/12/2026",
            fechaTermino = "22/12/2026",
            horario = "09:00",
            direccion = "Calle Arturo Prat 840",
            comuna = 7L,
            organizacion = 7L,
            cantAsistentes = 180,
            requisitos = "Confirmación de asistencia de dos representantes por empresa socia.",
            costosReferenciales = 190000,
            observaciones = "Desayuno empresarial y presentación de convenios 2027."
        ),
        EventoEntity(
            id = 8L,
            nombre = "Jornada Comunitaria de Servicios y Bienestar",
            tipo = "Actividad Municipal",
            fechaInicio = "28/12/2026",
            fechaTermino = "28/12/2026",
            horario = "10:00",
            direccion = "Plaza Cívica 320",
            comuna = 8L,
            organizacion = 8L,
            cantAsistentes = 400,
            requisitos = "Toldo plegable, mesón de atención y folletería informativa.",
            costosReferenciales = 130000,
            observaciones = "Actividad abierta a vecinos y organizaciones sociales de la comuna."
        )
    )
}