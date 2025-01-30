package com.example.parquesnaturales.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParqueNatural(
    @SerialName(value = "ID")
    val id: Int = 0,
    @SerialName(value = "Nombre")
    val nombre: String,
    @SerialName(value = "comunidad_autonomaid")
    val comunidadId: Int,
    @SerialName(value = "Extension")
    val extension: Double,
    @SerialName(value = "OrganismoID")
    val organismoId: Int,
    @SerialName(value = "TipoParqueID")
    val tipoParqueId: Int,
)
