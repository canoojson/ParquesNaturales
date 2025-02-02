package com.example.parquesnaturales.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.parquesnaturales.R
import com.example.parquesnaturales.modelo.Ruta
import com.example.parquesnaturales.ui.pantallas.PantallaActualizarEspecie
import com.example.parquesnaturales.ui.pantallas.PantallaActualizarEspecieRoom
import com.example.parquesnaturales.ui.pantallas.PantallaActualizarParque
import com.example.parquesnaturales.ui.pantallas.PantallaAjustes
import com.example.parquesnaturales.ui.pantallas.PantallaFavInicio
import com.example.parquesnaturales.ui.pantallas.PantallaInicioEspecies
import com.example.parquesnaturales.ui.pantallas.PantallaInicioParques
import com.example.parquesnaturales.ui.pantallas.PantallaInsertarEspecie
import com.example.parquesnaturales.ui.pantallas.PantallaInsertarParque

enum class Pantallas(@StringRes val titulo: Int){
    //Parques Naturales
    ParquesNaturales(titulo = R.string.parques_naturales),

    //Especies
    Especies(titulo = R.string.especies),

    //Ajustes
    Ajustes(titulo = R.string.ajustes),

    //Room
    Favoritos(titulo = R.string.favoritos),
    ActualizarEspecieRoom(titulo = R.string.actualizar_especie),

    //Insertar
    InsertarParque(titulo = R.string.insertar_parque),
    InsertarEspecie(titulo = R.string.insertar_especie),

    //Actualizar
    ActualizarParque(titulo = R.string.actualizar_parque),
    ActualizarEspecie(titulo = R.string.actualizar_especie)
}

val listaRutas = listOf(
    Ruta(Pantallas.ParquesNaturales.titulo, Pantallas.ParquesNaturales.name, R.drawable.hojallena, R.drawable.hojavacia),
    Ruta(Pantallas.Especies.titulo, Pantallas.Especies.name, R.drawable.carpincholleno, R.drawable.carpinchovacio),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParquesNaturalesApp(
    viewModelParque: ParqueNaturalViewModel = viewModel(factory = ParqueNaturalViewModel.Factory),
    viewModelEspecie: EspecieViewModel = viewModel(factory = EspecieViewModel.Factory),
    viewModelEspecieRoom: EspecieRoomViewModel = viewModel(factory = EspecieRoomViewModel.Factory),
    viewModelAjustes: AjustesViewModel = viewModel(factory = AjustesViewModel.Factory),
    navController: NavHostController = rememberNavController()
){
    val pilaRetroceso by navController.currentBackStackEntryAsState()

    val pantallaActual = Pantallas.valueOf(
        pilaRetroceso?.destination?.route ?: Pantallas.ParquesNaturales.name
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var selectedItem by remember { mutableIntStateOf(0)}

    Scaffold(
        topBar = {
            AppTopBar(
                pantallaActual = pantallaActual,
                puedeNavegarAtras = navController.previousBackStackEntry != null,
                scrollBehavior = scrollBehavior,
                navController = navController,
                onNavegarAtras = { navController.navigateUp() }
            )
        },
        bottomBar = {
            NavigationBar{
                listaRutas.forEachIndexed{ indice, ruta ->
                    NavigationBarItem(
                        icon ={
                            if(selectedItem == indice){
                                Image(painter = painterResource(id = ruta.iconoLleno),
                                    contentDescription = stringResource(id = ruta.nombre),
                                    modifier= Modifier.size(18.dp))
                            }else{
                                Image(painter = painterResource(id = ruta.iconoVacio),
                                    contentDescription = stringResource(id = ruta.nombre),
                                    modifier= Modifier.size(18.dp))
                            }
                        },
                        label = {Text(text = stringResource(id = ruta.nombre))},
                        selected = selectedItem == indice,
                        onClick = { selectedItem = indice
                                    navController.navigate(ruta.ruta)}
                    )

                }
            }
        },
        floatingActionButton = {
            if(pantallaActual.titulo == Pantallas.ParquesNaturales.titulo){
                FloatingActionButton(onClick = { navController.navigate(Pantallas.InsertarParque.name)}) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.insertar)
                    )
                }
            }else if(pantallaActual.titulo == Pantallas.Especies.titulo){
                FloatingActionButton(onClick = { navController.navigate(Pantallas.InsertarEspecie.name)}) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.insertar)
                    )
                }
            }
        }
    ) { innerPadding ->

        val uiStateParque = viewModelParque.parqueUIState
        val uiStateEspecie = viewModelEspecie.especieUIState
        val uiStateEspecieRoom = viewModelEspecieRoom.especieRoomUIState

        NavHost(
            navController = navController,
            startDestination = Pantallas.ParquesNaturales.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = Pantallas.ParquesNaturales.name){
                PantallaInicioParques(
                    appUIState = uiStateParque,
                    onParquesObtenidos = {viewModelParque.obtenerParques()},
                    onParquePulsado = {
                        viewModelParque.actualizarParquePulsado(it)
                        navController.navigate(Pantallas.ActualizarParque.name)
                    },
                    onParqueEliminado = {viewModelParque.eliminarParque(it)}
                )
            }
            composable(route = Pantallas.Especies.name){
                PantallaInicioEspecies(
                    appUIState= uiStateEspecie,
                    onEspeciesObtenidas = {viewModelEspecie.obtenerEspecies()},
                    onEspeciePulsada = {
                        viewModelEspecie.actualizarEspeciePulsada(it)
                        navController.navigate(Pantallas.ActualizarEspecie.name)
                    },
                    onEspecieEliminada = {viewModelEspecie.eliminarEspecie(it)},
                    onEspecieGuardada = {viewModelEspecieRoom.insertarEspecie(it)}
                )
            }
            composable(route= Pantallas.Favoritos.name){
                PantallaFavInicio(
                    appUIState = uiStateEspecieRoom,
                    onEspeciesObtenidas = {viewModelEspecieRoom.obtenerEspecies()},
                    onEspeciePulsada = {
                        viewModelEspecieRoom.obtenerEspecie(it)
                        navController.navigate(Pantallas.ActualizarEspecieRoom.name)
                    },
                    onEspecieEliminada = {viewModelEspecieRoom.eliminarEspecie(it)},
                )
            }
            composable(route= Pantallas.ActualizarEspecieRoom.name){
                Log.v("EspeciePulsada", viewModelEspecieRoom.especiePulsada.toString())
                PantallaActualizarEspecieRoom(
                    especie = viewModelEspecieRoom.especiePulsada,
                    onEspecieActualizada = {
                        viewModelEspecieRoom.actualizarEspecie(it)
                        navController.popBackStack(Pantallas.Favoritos.name, inclusive = false)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = Pantallas.Ajustes.name){
                PantallaAjustes(onBorrarFavoritosPulsado = {viewModelAjustes.vaciarDB()})
            }
            composable(route = Pantallas.InsertarParque.name){
                PantallaInsertarParque(
                    onInsertarPulsado = {
                        viewModelParque.insertarParque(it)
                        navController.navigate(Pantallas.ParquesNaturales.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = Pantallas.InsertarEspecie.name){
                PantallaInsertarEspecie(
                    onInsertarPulsado = {
                        viewModelEspecie.insertarEspecie(it)
                        navController.navigate(Pantallas.Especies.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = Pantallas.ActualizarParque.name){
                PantallaActualizarParque(
                    parque = viewModelParque.parquePulsado,
                    onActualizarPulsado = {
                        viewModelParque.actualizarParque(it.id, it)
                        navController.navigate(Pantallas.ParquesNaturales.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = Pantallas.ActualizarEspecie.name) {
                PantallaActualizarEspecie(
                    especie = viewModelEspecie.especiePulsada,
                    onActualizarPulsado = {
                        viewModelEspecie.actualizarEspecie(it.id, it)
                        navController.navigate(Pantallas.Especies.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: Pantallas,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit,
    modifier: Modifier = Modifier
){

    var mostrarMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(stringResource(id = pantallaActual.titulo)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if(puedeNavegarAtras && pantallaActual.titulo != Pantallas.ParquesNaturales.titulo && pantallaActual.titulo != Pantallas.Especies.titulo) {
                IconButton(onClick = onNavegarAtras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.atras)
                    )
                }
            }
        },
        actions = {
            if(pantallaActual.titulo == Pantallas.ParquesNaturales.titulo
                || pantallaActual.titulo == Pantallas.Especies.titulo){
                IconButton(onClick = { mostrarMenu = true}) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = stringResource(id = R.string.masopciones)
                    )
                }
                DropdownMenu(
                    mostrarMenu, {mostrarMenu = false}
                ){
                    DropdownMenuItem(text = { Text(stringResource(id = R.string.favoritos)) },
                        onClick = {
                            mostrarMenu = false
                            navController.navigate(Pantallas.Favoritos.name)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.ajustes)) },
                        onClick = {
                            mostrarMenu = false
                            navController.navigate(Pantallas.Ajustes.name)
                        }
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )

}