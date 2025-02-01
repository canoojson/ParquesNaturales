package com.example.parquesnaturales.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "especie")
data class EspecieRoom(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nombre: String,
    var tipo: String,
    var anotaciones: String
)
