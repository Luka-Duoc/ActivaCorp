package com.example.activacorp.data.local.seed
import com.example.activacorp.model.TipoOportunidad
import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.model.EstadoGestion
import com.example.activacorp.model.PotencialComercial

object OportunidadSeed {
    val listaOportunidades = listOf(
        OportunidadEntity(
            nombre = "Encuentro anual de colaboradores",
            tipo = TipoOportunidad.EVENTO_CORPORATIVO,
            descripcion = "Encuentro interno de una empresa con espacios para presentar beneficios y servicios.",
            fuente = "Sitio web institucional",
            fechaDeteccion = "2026-10-02",
            responsable = 1L,
            prioridad = 5,
            potencial = PotencialComercial.ALTO,
            estado = EstadoGestion.CONTACTO_PENDIENTE,
            fechaActualizacion = "2026-10-15",
            observaciones = "Consultar cantidad estimada de asistentes y disponibilidad de stands."
        ),
        OportunidadEntity(
            nombre = "Feria de proveedores y servicios empresariales",
            tipo = TipoOportunidad.FERIA_EMPRESARIAL,
            descripcion = "Feria orientada a conectar empresas con proveedores de productos y servicios.",
            fuente = "LinkedIn",
            fechaDeteccion = "2026-10-04",
            responsable = 2L,
            prioridad = 5,
            potencial = PotencialComercial.ALTO,
            estado = EstadoGestion.CONTACTO_REALIZADO,
            fechaActualizacion = "2026-10-16",
            observaciones = "Se envió un primer mensaje; pendiente confirmar quién coordina la feria."
        ),
        OportunidadEntity(
            nombre = "Jornada municipal de servicios comunitarios",
            tipo = TipoOportunidad.ACTIVIDAD_MUNICIPAL,
            descripcion = "Actividad abierta a la comunidad con módulos informativos y servicios para vecinos.",
            fuente = "Calendario municipal",
            fechaDeteccion = "2026-09-28",
            responsable = 3L,
            prioridad = 4,
            potencial = PotencialComercial.MEDIO,
            estado = EstadoGestion.EVENTO_IDENTIFICADO,
            fechaActualizacion = "2026-10-01",
            observaciones = "Verificar fecha definitiva, requisitos de participación y costo del espacio."
        ),
        OportunidadEntity(
            nombre = "Día de beneficios para funcionarios",
            tipo = TipoOportunidad.JORNADA_DE_BENEFICIOS,
            descripcion = "Jornada para dar a conocer convenios, descuentos y beneficios para trabajadores.",
            fuente = "Contacto referido",
            fechaDeteccion = "2026-10-06",
            responsable = 1L,
            prioridad = 5,
            potencial = PotencialComercial.ALTO,
            estado = EstadoGestion.PROPUESTA_ENVIADA,
            fechaActualizacion = "2026-10-14",
            observaciones = "Propuesta enviada con alternativas de activación y material informativo."
        ),
        OportunidadEntity(
            nombre = "Feria de innovación institucional",
            tipo = TipoOportunidad.FERIA_INSTITUCIONAL,
            descripcion = "Instancia de una institución para exhibir iniciativas, servicios y proyectos.",
            fuente = "Boletín institucional",
            fechaDeteccion = "2026-10-03",
            responsable = 4L,
            prioridad = 3,
            potencial = PotencialComercial.MEDIO,
            estado = EstadoGestion.EN_ESPERA_DE_RESPUESTA,
            fechaActualizacion = "2026-10-12",
            observaciones = "Se solicitó información sobre bases, fechas y condiciones para expositores."
        ),
        OportunidadEntity(
            nombre = "Actividad recreativa para trabajadores",
            tipo = TipoOportunidad.ACTIVIDAD_DE_TRABAJADORES,
            descripcion = "Actividad recreativa interna con participación de trabajadores y sus familias.",
            fuente = "Correo de contacto",
            fechaDeteccion = "2026-09-25",
            responsable = 2L,
            prioridad = 3,
            potencial = PotencialComercial.MEDIO,
            estado = EstadoGestion.PARTICIPACION_CONFIRMADA,
            fechaActualizacion = "2026-10-10",
            observaciones = "Participación confirmada de forma preliminar; coordinar montaje y horario."
        ),
        OportunidadEntity(
            nombre = "Activación promocional en centro comercial",
            tipo = TipoOportunidad.ACTIVACION_COMERCIAL,
            descripcion = "Espacio promocional para interactuar con visitantes y difundir una campaña comercial.",
            fuente = "Página de contacto del centro comercial",
            fechaDeteccion = "2026-10-08",
            responsable = 3L,
            prioridad = 4,
            potencial = PotencialComercial.ALTO,
            estado = EstadoGestion.CONTACTO_PENDIENTE,
            fechaActualizacion = "2026-10-08",
            observaciones = "Solicitar tarifas, dimensiones disponibles y restricciones de montaje."
        ),
        OportunidadEntity(
            nombre = "Encuentro de organizaciones comunitarias",
            tipo = TipoOportunidad.OTRA_INSTANCIA,
            descripcion = "Encuentro de organizaciones locales con posibilidad de colaboración y difusión.",
            fuente = "Red de contactos",
            fechaDeteccion = "2026-09-30",
            responsable = 4L,
            prioridad = 2,
            potencial = PotencialComercial.BAJO,
            estado = EstadoGestion.OPORTUNIDAD_DESCARTADA,
            fechaActualizacion = "2026-10-05",
            observaciones = "Descartada por baja coincidencia entre el público asistente y el público objetivo."
        ),
        OportunidadEntity(
            nombre = "Congreso de gestión y recursos humanos",
            tipo = TipoOportunidad.EVENTO_CORPORATIVO,
            descripcion = "Congreso profesional con asistentes de áreas de gestión de personas y bienestar laboral.",
            fuente = "Agenda de eventos",
            fechaDeteccion = "2026-10-09",
            responsable = 1L,
            prioridad = 4,
            potencial = PotencialComercial.ALTO,
            estado = EstadoGestion.EVENTO_IDENTIFICADO,
            fechaActualizacion = "2026-10-09",
            observaciones = "Investigar perfil de asistentes y plazos para solicitar participación."
        ),
        OportunidadEntity(
            nombre = "Feria de emprendimiento local",
            tipo = TipoOportunidad.FERIA_EMPRESARIAL,
            descripcion = "Feria con emprendimientos y comercios locales, abierta a residentes de la comuna.",
            fuente = "Redes sociales del organizador",
            fechaDeteccion = "2026-10-11",
            responsable = 2L,
            prioridad = 2,
            potencial = PotencialComercial.MEDIO,
            estado = EstadoGestion.CONTACTO_PENDIENTE,
            fechaActualizacion = "2026-10-11",
            observaciones = "Evaluar afluencia esperada y pertinencia de la activación antes de contactar."
        ),
        OportunidadEntity(
            nombre = "Semana de bienestar y salud laboral",
            tipo = TipoOportunidad.JORNADA_DE_BENEFICIOS,
            descripcion = "Semana temática dedicada al bienestar laboral, beneficios y calidad de vida.",
            fuente = "Contacto con área de personas",
            fechaDeteccion = "2026-10-07",
            responsable = 3L,
            prioridad = 5,
            potencial = PotencialComercial.ALTO,
            estado = EstadoGestion.CONTACTO_REALIZADO,
            fechaActualizacion = "2026-10-17",
            observaciones = "La organización mostró interés; preparar propuesta según cantidad de trabajadores."
        ),
        OportunidadEntity(
            nombre = "Muestra de servicios para la comunidad educativa",
            tipo = TipoOportunidad.FERIA_INSTITUCIONAL,
            descripcion = "Muestra de servicios y actividades para estudiantes, trabajadores y familias.",
            fuente = "Sitio web de la institución",
            fechaDeteccion = "2026-10-13",
            responsable = 4L,
            prioridad = 3,
            potencial = PotencialComercial.BAJO,
            estado = EstadoGestion.EVENTO_IDENTIFICADO,
            fechaActualizacion = "2026-10-13",
            observaciones = "Confirmar si se permite la participación de entidades externas."
        ),
        OportunidadEntity(
            nombre = "Campaña de descuentos de temporada",
            tipo = TipoOportunidad.ACTIVACION_COMERCIAL,
            descripcion = "Campaña temporal con potencial de difusión y captación de público en punto de venta.",
            fuente = "Contacto comercial",
            fechaDeteccion = "2026-10-14",
            responsable = 2L,
            prioridad = 4,
            potencial = PotencialComercial.MEDIO,
            estado = EstadoGestion.EN_ESPERA_DE_RESPUESTA,
            fechaActualizacion = "2026-10-16",
            observaciones = "Esperando confirmación de fechas, presupuesto y condiciones comerciales."
        ),
        OportunidadEntity(
            nombre = "Jornada de vinculación con empresas",
            tipo = TipoOportunidad.OTRA_INSTANCIA,
            descripcion = "Actividad de networking para facilitar el contacto entre organizaciones y empresas.",
            fuente = "Asociación empresarial",
            fechaDeteccion = "2026-10-15",
            responsable = 1L,
            prioridad = 3,
            potencial = PotencialComercial.MEDIO,
            estado = EstadoGestion.CONTACTO_PENDIENTE,
            fechaActualizacion = "2026-10-15",
            observaciones = "Identificar al encargado de alianzas y consultar modalidad de participación."
        ),
        OportunidadEntity(
            nombre = "Celebración institucional de fin de año",
            tipo = TipoOportunidad.EVENTO_CORPORATIVO,
            descripcion = "Celebración interna de cierre de año con actividades para colaboradores.",
            fuente = "Referencia de un contacto",
            fechaDeteccion = "2026-10-16",
            responsable = 3L,
            prioridad = 4,
            potencial = PotencialComercial.ALTO,
            estado = EstadoGestion.CONTACTO_PENDIENTE,
            fechaActualizacion = "2026-10-16",
            observaciones = "Consultar fecha, número de asistentes y si aceptan proveedores externos."
        )
    )
}