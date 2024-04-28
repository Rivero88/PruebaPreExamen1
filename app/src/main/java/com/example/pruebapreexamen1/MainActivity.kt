package com.example.pruebapreexamen1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pruebapreexamen1.data.AcademiaViewModel
import com.example.pruebapreexamen1.data.AcademisUiState
import com.example.pruebapreexamen1.data.Asignatura
import com.example.pruebapreexamen1.data.DataSource
import com.example.pruebapreexamen1.ui.theme.PruebaPreExamen1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaPreExamen1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPrincipal(asignaturas = DataSource.asignaturas,
                        viewModelAcademia = viewModel()
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(asignaturas : ArrayList<Asignatura>,
                      viewModelAcademia : AcademiaViewModel) {
    val uiState by viewModelAcademia.uiState.collectAsState()
    Column (modifier = Modifier) {
        Text(text = "Bienvenid@ Academia Ester Rivero",
            Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(start = 20.dp, top = 20.dp))
        PantallaAsignaturas(asignaturas = asignaturas, viewModelAcademia = viewModelAcademia)
        PantallaTextEditor(viewModelAcademia = viewModelAcademia)
        PantallaTextos(uiState = uiState )
    }
}

@Composable
fun PantallaAsignaturas(modifier: Modifier = Modifier, asignaturas : ArrayList<Asignatura>,
                        viewModelAcademia : AcademiaViewModel){
    Column(modifier.height(300.dp)) {
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)){
            items(asignaturas){asignatura ->
                Card(
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp)){
                    Text(text = "Asig: ${asignatura.nombre}",
                        modifier
                            .fillMaxWidth()
                            .background(Color.Yellow)
                            .padding(8.dp),
                        textAlign = TextAlign.Center)
                    Text(text = "€/hora: ${asignatura.precioHora}",
                        modifier
                            .fillMaxWidth()
                            .background(Color.Cyan)
                            .padding(8.dp),
                        textAlign = TextAlign.Center)
                    Row(modifier.align(Alignment.CenterHorizontally)) {
                        Button(onClick = { viewModelAcademia.SumarHoras(asignatura, asignaturas, viewModelAcademia.valorHoras) },
                            modifier.padding(6.dp)) {
                            Text(text = "+")
                        }
                        Button(onClick = { viewModelAcademia.RestarHoras(asignatura, asignaturas, viewModelAcademia.valorHoras) },
                            modifier.padding(6.dp)) {
                            Text(text = "-")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTextEditor (viewModelAcademia : AcademiaViewModel){
    TextField(value = viewModelAcademia.valorHoras,
        onValueChange = {viewModelAcademia.NuevoValorHoras(it)},
        label = { Text(text = "Horas a contratar o eliminar")},
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    )
}

@Composable
fun PantallaTextos(modifier: Modifier = Modifier, uiState: AcademisUiState){
    Column (modifier.fillMaxWidth()
        .background(Color.LightGray)
        .padding(8.dp)) {
        Text(text = "Última acción: \n ${uiState.textoUltAccion}",
            modifier.fillMaxWidth()
                .background(Color.Magenta))
        Text(text = "Resumen: \n ${uiState.textoResumen}",
            modifier.fillMaxWidth()
                .background(Color.White))
    }
}