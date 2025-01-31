package com.example.parquesnaturales.datos

import com.example.parquesnaturales.conexion.ParquesNaturalesApi
import com.example.parquesnaturales.modelo.ParqueNatural
import retrofit2.Response

interface ParqueRepositorio{
    suspend fun obtenerParques(): List<ParqueNatural>
    suspend fun insertarParque(parque: ParqueNatural): ParqueNatural
    suspend fun actualizarParque(id : Int, parque: ParqueNatural): Response<ParqueNatural>
    suspend fun eliminarParque(id: Int): Response<ParqueNatural>
}

class ConexionParqueRepositorio(
    private val api: ParquesNaturalesApi
): ParqueRepositorio{
    override suspend fun obtenerParques(): List<ParqueNatural> = api.obtenerParques()
    override suspend fun insertarParque(parque: ParqueNatural): ParqueNatural = api.insertarParque(parque)
    override suspend fun actualizarParque(id: Int, parque: ParqueNatural): Response<ParqueNatural> = api.actualizarParque(id, parque)
    override suspend fun eliminarParque(id: Int): Response<ParqueNatural> = api.eliminarParque(id)
}