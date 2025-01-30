package com.example.parquesnaturales.datos

import com.example.parquesnaturales.conexion.ParquesNaturalesApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorApp{
    val parqueRepositorio: ParqueRepositorio
}

class ParqueContenedorApp : ContenedorApp{
    private val baseUrl = "http://10.0.2.2:3000"

    private val json = Json { ignoreUnknownKeys = true}

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioretrofit: ParquesNaturalesApi by lazy {
        retrofit.create(ParquesNaturalesApi::class.java)
    }

    override val parqueRepositorio: ParqueRepositorio by lazy {
        ConexionParqueRepositorio(servicioretrofit)
    }

}