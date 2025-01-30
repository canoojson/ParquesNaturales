package com.example.parquesnaturales.conexion

import com.example.parquesnaturales.modelo.ParqueNatural
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT

interface ParquesNaturalesApi {
    @GET("parquesnaturales")
    suspend fun obtenerParques(): List<ParqueNatural>

    @POST("parquesnaturales")
    suspend fun insertarParque(
        @Body parque: ParqueNatural
    ): ParqueNatural

    @PUT("parquesnaturales/{id}")
    suspend fun actualizarParque(
        @Path("id") id: Int,
        @Body parque: ParqueNatural
    ): ParqueNatural

    @DELETE("parquesnaturales/{id}")
    suspend fun eliminarParque(
        @Path("id") id: Int
    ): ParqueNatural
}