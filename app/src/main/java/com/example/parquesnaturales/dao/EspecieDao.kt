package com.example.parquesnaturales.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.parquesnaturales.modelo.EspecieRoom

@Dao
interface EspecieDao {
    @Query("SELECT * FROM especie ORDER BY nombre ASC")
    suspend fun obtenerEspecies(): List<EspecieRoom>
    @Query("SELECT * FROM especie WHERE id = :id")
    suspend fun obtenerEspecie(id: Int): EspecieRoom
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarEspecie(especie: EspecieRoom)
    @Update
    suspend fun actualizarEspecie(especie: EspecieRoom)
    @Delete
    suspend fun eliminarEspecie(especie: EspecieRoom)
}