package com.example.parquesnaturales.conexion

import com.example.parquesnaturales.modelo.Especie
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EspeciesApi {
    @GET("especies")
    suspend fun obtenerEspecies(): List<Especie>

    @POST("especies")
    suspend fun insertarEspecie(
        @Body especie: Especie
    ): Especie

    @PUT("especies/{id}")
    suspend fun actualizarEspecie(
        @Path("id") id: Int,
        @Body especie: Especie
    ): Response<Especie>

    @DELETE("especies/{id}")
    suspend fun eliminarEspecie(
        @Path("id") id: Int
    ): Response<Especie>
}