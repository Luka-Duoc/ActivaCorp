package com.example.activacorp.model.enums

enum class TipoOportunidad(val nombreMuestra: String) {
    EVENTO_CORPORATIVO("Evento corporativo"),
    FERIA_EMPRESARIAL(nombreMuestra = "Feria empresarial"),
    ACTIVIDAD_MUNICIPAL(nombreMuestra = "Actividad municipal"),
    JORNADA_DE_BENEFICIOS("Jornada de beneficios"),
    FERIA_INSTITUCIONAL("Feria institucional"),
    ACTIVIDAD_DE_TRABAJADORES("Actividad de trabajadores"),
    ACTIVACION_COMERCIAL("Activación comercial"),
    OTRA_INSTANCIA("Otra instancia")
}