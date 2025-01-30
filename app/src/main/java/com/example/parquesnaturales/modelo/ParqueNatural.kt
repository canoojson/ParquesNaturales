package com.example.parquesnaturales.modelo

import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParqueNatural(
    @SerialName(value = "id")
    val id: Int = 0,
    @SerialName(value = "nombre")
    val nombre: String,
    @SerialName(value = "extension")
    val extension: Double,
)
