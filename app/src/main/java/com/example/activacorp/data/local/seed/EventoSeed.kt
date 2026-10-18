package com.example.activacorp.data.local.seed

import com.example.activacorp.data.local.entity.EventoEntity

object EventoSeed {
    val listaEventos = listOf(
        EventoEntity(
            id = 1L,
            nombre = "Expo Bienestar y Salud Laboral 2026",
            tipo = "Feria Empresarial",
            fechaInicio = "2026-11-10",
            fechaTermino = "2026-11-12",
            horario = "09:00 - 18:00",
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
            fechaInicio = "2026-11-20",
            fechaTermino = "2026-11-20",
            horario = "10:00 - 15:00",
            direccion = "Parque O'Higgins s/n",
            comuna = 1L,
            organizacion = 1L,
            cantAsistentes = 200,
            requisitos = "Indumentaria deportiva, hidratación provista por organizadores.",
            costosReferenciales = 150000,
            observaciones = "Punto de contacto coordinado con recursos humanos."
        ),
        EventoEntity(
            id = 3L,
            nombre = "Congreso Anual de Gestión de Personas",
            tipo = "Evento Corporativo",
            fechaInicio = "2026-12-05",
            fechaTermino = "2026-12-06",
            horario = "08:30 - 17:30",
            direccion = "Av. Santa María 2050",
            comuna = 1L,
            organizacion = 2L,
            cantAsistentes = 600,
            requisitos = "Inscripción en plataforma del congreso y pago de matrícula institucional.",
            costosReferenciales = 500000,
            observaciones = "Oportunidad de patrocinio en etapa de cierre."
        )
    )
}