package com.example.parquesnaturales.ui.pantallas


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parquesnaturales.R
import com.example.parquesnaturales.modelo.Especie

@Composable
fun PantallaActualizarEspecie(
    especie: Especie,
    onActualizarPulsado:(Especie) -> Unit,
    modifier: Modifier = Modifier
){
    var nombre by remember { mutableStateOf(especie.nombre)}
    var descripcion by remember { mutableStateOf(
        if(especie.descripcion.isNullOrEmpty()){
            ""
        }else{
            especie.descripcion
        }) }
    var tipo by remember { mutableStateOf(
        if(especie.tipo.isNullOrEmpty()){
            ""
        }else{
            especie.tipo
        }) }
    var errorNombre by remember { mutableStateOf(false) }
    var errorTipo by remember { mutableStateOf(false) }
    var errorDescripcion by remember { mutableStateOf(false) }

    val contexto = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))

        TextField(
            value = nombre,
            onValueChange = {
                if(it.all { it.isLetter() || it == ' '}){
                    nombre = it
                }
            },
            label = { Text(stringResource(id = R.string.nombre))},
            isError = errorNombre
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = tipo,
            onValueChange = {
                if(it.all { it.isLetter()|| it == ' ' }){
                    tipo = it
                }
            },
            label = { Text(stringResource(id = R.string.tipo))},
            isError = errorTipo
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = descripcion,
            onValueChange = {
                if(it.all { it.isLetter()|| it == ' ' }){
                    descripcion = it
                }
            },
            label = { Text(stringResource(id = R.string.descripcion))},
            isError = errorDescripcion
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            val especie = Especie(id= especie.id,nombre = nombre, tipo = tipo, descripcion = descripcion)
            if(especie.nombre.isBlank()){
                errorNombre = true
            }
            if(errorNombre){
                Toast.makeText(contexto, "Datos no introducidos. Porfavor rellene todos los campos", Toast.LENGTH_SHORT).show()
            }else{
                onActualizarPulsado(especie)
            }
        }) {
            Text(stringResource(id = R.string.insertar))
        }
    }
}