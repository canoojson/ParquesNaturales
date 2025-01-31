package com.example.parquesnaturales.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.parquesnaturales.R
import com.example.parquesnaturales.modelo.ParqueNatural

@Composable
fun PantallaActualizarParque(
    parque: ParqueNatural,
    onActualizarPulsado:(ParqueNatural) -> Unit,
    modifier: Modifier = Modifier
){
    var nombre by remember { mutableStateOf(parque.nombre)}
    var extensionTexto by remember { mutableStateOf(parque.extension.toString()) }
    var extension by remember { mutableStateOf(parque.extension) }
    var especies by remember { mutableStateOf(parque.especies) }

    var errorNombre by remember { mutableStateOf(false) }
    var errorExtension by remember { mutableStateOf(false) }

    val contexto = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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
            value = extensionTexto,
            onValueChange = {
                if(it.all { it.isDigit() || it == '.' }){
                    extensionTexto = it
                    extension = it.toDoubleOrNull() ?: 0.0
                }
            },
            label = { Text(stringResource(id = R.string.extension)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            isError = errorExtension
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            val parque = ParqueNatural(id= parque.id,nombre = nombre, extension = extension, especies = especies )
            if(parque.nombre.isEmpty()){
                errorNombre = true
            }
            if(parque.extension == 0.0){
                errorExtension = true
            }
            if(errorNombre || errorExtension){
                Toast.makeText(contexto, "Datos no introducidos. Porfavor rellene todos los campos", Toast.LENGTH_SHORT).show()
            }else{
                onActualizarPulsado(parque)
            }
        }) {
            Text(stringResource(id = R.string.actualizar))
        }
    }
}