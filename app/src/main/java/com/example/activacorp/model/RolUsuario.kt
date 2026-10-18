package com.example.activacorp.model

enum class RolUsuario(val titulo: String, val descripcion: String) {
    EJECUTIVO("Ejecutivo Comercial", "Gestion y Registro de oprtunidades"),
    SUPERVISOR("Supervisor Comercial", "Supervision y metricas del equipo"),
    MARKETING("Marketing y comunicaciones", "Material de apoyo y logistico");

    companion object {
        fun asignarCorreo(correo: String): RolUsuario? {
            val c = correo.trim().lowercase()
            return when {
                c.endsWith("@comercial.cl") || c.endsWith("@ejecutivo.cl") -> EJECUTIVO
                c.endsWith("@supervisor.cl") -> SUPERVISOR
                c.endsWith("marketing.cl") -> MARKETING
                else -> null
            }
        }
    }

}
