package com.example.parquesnaturales.datos

import android.content.Context
import com.example.parquesnaturales.conexion.EspeciesApi
import com.example.parquesnaturales.conexion.EspeciesBaseDatos
import com.example.parquesnaturales.conexion.ParquesNaturalesApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorApp{
    val parqueRepositorio: ParqueRepositorio
    val especieRepositorio: EspecieRepositorio
    val especieRoomRepositorio: EspecieRoomRepositorio
}

class ParqueContenedorApp(private val context: Context) : ContenedorApp{
    private val baseUrl = "http://192.168.1.31:8080/api/"

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioretrofitParques: ParquesNaturalesApi by lazy {
        retrofit.create(ParquesNaturalesApi::class.java)
    }

    private val servicioretrofitEspecies: EspeciesApi by lazy {
        retrofit.create(EspeciesApi::class.java)
    }

    override val parqueRepositorio: ParqueRepositorio by lazy {
        ConexionParqueRepositorio(servicioretrofitParques)
    }

    override val especieRepositorio: EspecieRepositorio by lazy {
        ConexionEspecieRepositorio(servicioretrofitEspecies)
    }

    override val especieRoomRepositorio: EspecieRoomRepositorio by lazy {
        ConexionEspecieRoomRepositorio(EspeciesBaseDatos.obtenerBaseDatos(context = context).especieDao())
    }

}