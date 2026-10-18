package com.example.activacorp.model

import com.example.activacorp.data.local.entity.EventoEntity
import org.json.JSONObject

object EventoQr {
    fun escanear(jsonString: String): EventoEntity? {
        return try {
            val json = JSONObject(jsonString)
            EventoEntity(
                id = 0,
                nombre = json.optString("nombre", "Evento QR"),
                tipo = json.optString("tipo", "General"),
                fechaInicio = json.optString("fechaInicio", ""),
                fechaTermino = json.optString("fechaTermino", ""),
                horario = json.optString("horario", ""),
                direccion = json.optString("direccion", ""),
                comuna = json.optLong("comuna", 1L),
                organizacion = json.optLong("organizacion", 1L),
                cantAsistentes = json.optInt("cantAsistentes", 0),
                requisitos = json.optString("requisitos", ""),
                costosReferenciales = json.optInt("costosReferenciales", 0),
                observaciones = json.optString("observaciones", "Registrado vía QR")
            )
        } catch (e: Exception) {
            null
        }
    }
}