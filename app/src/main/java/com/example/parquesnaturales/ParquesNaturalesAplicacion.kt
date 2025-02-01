package com.example.parquesnaturales

import android.app.Application
import com.example.parquesnaturales.datos.ContenedorApp
import com.example.parquesnaturales.datos.ParqueContenedorApp

class ParquesNaturalesAplicacion: Application() {
    lateinit var contenedor: ContenedorApp
    override fun onCreate() {
        super.onCreate()
        contenedor = ParqueContenedorApp(this)
    }
}