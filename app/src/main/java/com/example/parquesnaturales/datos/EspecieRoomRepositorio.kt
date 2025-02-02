package com.example.parquesnaturales.datos

import com.example.parquesnaturales.dao.EspecieDao
import com.example.parquesnaturales.modelo.EspecieRoom

interface EspecieRoomRepositorio {
    suspend fun obtenerTodasEspecies(): List<EspecieRoom>
    suspend fun obtenerEspecie(id: Int): EspecieRoom
    suspend fun insertar(especie: EspecieRoom)
    suspend fun actualizar(especie: EspecieRoom)
    suspend fun eliminar(especie: EspecieRoom)
    suspend fun vaciarDB()
}

class ConexionEspecieRoomRepositorio(
    private val especieDao: EspecieDao
): EspecieRoomRepositorio {
    override suspend fun obtenerTodasEspecies(): List<EspecieRoom> = especieDao.obtenerEspecies()
    override suspend fun obtenerEspecie(id: Int): EspecieRoom = especieDao.obtenerEspecie(id)
    override suspend fun insertar(especie: EspecieRoom) = especieDao.insertarEspecie(especie)
    override suspend fun actualizar(especie: EspecieRoom) = especieDao.actualizarEspecie(especie)
    override suspend fun eliminar(especie: EspecieRoom) = especieDao.eliminarEspecie(especie)
    override suspend fun vaciarDB() {
        especieDao.vaciarDB()
        especieDao.reiniciarContador()
    }
}