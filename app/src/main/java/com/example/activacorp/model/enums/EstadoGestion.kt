package com.example.activacorp.model.enums

enum class EstadoGestion(val nombreMuestra: String){
    EVENTO_IDENTIFICADO("Evento identificado"),
    CONTACTO_PENDIENTE("Contacto pendiente"),
    CONTACTO_REALIZADO("Contacto realizado"),
    PROPUESTA_ENVIADA("Propuesta enviada"),
    EN_ESPERA_DE_RESPUESTA("En espera de respuesta"),
    PARTICIPACION_CONFIRMADA("Participación confirmada"),
    OPORTUNIDAD_DESCARTADA("Oportunidad descartada")
}