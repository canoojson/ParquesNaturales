package com.example.parquesnaturales.conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parquesnaturales.dao.EspecieDao
import com.example.parquesnaturales.modelo.EspecieRoom

@Database(entities = [EspecieRoom::class], version = 1, exportSchema = false)
abstract class EspeciesBaseDatos: RoomDatabase() {
    abstract fun especieDao(): EspecieDao

    companion object{
        @Volatile
        private var Instance: EspeciesBaseDatos? = null

        fun obtenerBaseDatos(context: Context): EspeciesBaseDatos {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, EspeciesBaseDatos::class.java, "especiedb")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}