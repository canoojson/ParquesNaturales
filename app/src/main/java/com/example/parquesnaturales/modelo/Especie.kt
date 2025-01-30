package com.example.parquesnaturales.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Especie(
    @SerialName(value = "ID")
    val id : Int = 0,
    @SerialName(value = "Nombre")
    val nombre : String,
    @SerialName(value = "Descripcion")
    val descripcion : String,
    @SerialName(value = "Tipo")
    val tipo : String
)
