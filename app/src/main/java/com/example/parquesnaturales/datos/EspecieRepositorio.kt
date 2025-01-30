package com.example.parquesnaturales.datos

import com.example.parquesnaturales.conexion.EspeciesApi
import com.example.parquesnaturales.conexion.ParquesNaturalesApi
import com.example.parquesnaturales.modelo.Especie
import com.example.parquesnaturales.modelo.ParqueNatural

interface EspecieRepositorio{
    suspend fun obtenerEspecies(): List<Especie>
    suspend fun insertarEspecie(especie: Especie): Especie
    suspend fun actualizarEspecie(id : Int, especie: Especie): Especie
    suspend fun eliminarEspecie(id: Int): Especie
}

class ConexionEspecieRepositorio(
    private val api: EspeciesApi
): EspecieRepositorio{
    override suspend fun obtenerEspecies(): List<Especie> = api.obtenerEspecies()
    override suspend fun insertarEspecie(especie: Especie): Especie = api.insertarEspecie(especie)
    override suspend fun actualizarEspecie(id: Int, especie: Especie): Especie = api.actualizarEspecie(id, especie)
    override suspend fun eliminarEspecie(id: Int): Especie = api.eliminarEspecie(id)
}