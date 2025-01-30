package com.example.parquesnaturales.datos

import com.example.parquesnaturales.conexion.ParquesNaturalesApi
import com.example.parquesnaturales.modelo.ParqueNatural

interface ParqueRepositorio{
    suspend fun obtenerParques(): List<ParqueNatural>
    suspend fun insertarParque(parque: ParqueNatural): ParqueNatural
    suspend fun actualizarParque(id : Int, parque: ParqueNatural): ParqueNatural
    suspend fun eliminarParque(id: Int): ParqueNatural
}

class ConexionParqueRepositorio(
    private val api: ParquesNaturalesApi
): ParqueRepositorio{
    override suspend fun obtenerParques(): List<ParqueNatural> = api.obtenerParques()
    override suspend fun insertarParque(parque: ParqueNatural): ParqueNatural = api.insertarParque(parque)
    override suspend fun actualizarParque(id: Int, parque: ParqueNatural): ParqueNatural = api.actualizarParque(id, parque)
    override suspend fun eliminarParque(id: Int): ParqueNatural = api.eliminarParque(id)
}