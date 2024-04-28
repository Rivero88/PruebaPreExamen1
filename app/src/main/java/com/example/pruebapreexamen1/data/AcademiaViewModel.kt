package com.example.pruebapreexamen1.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AcademiaViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AcademisUiState())
    val uiState: StateFlow<AcademisUiState> = _uiState.asStateFlow()

    var valorHoras by mutableStateOf("")

    fun NuevoValorHoras (nuevoValorHoras: String){
        valorHoras = nuevoValorHoras
    }

    fun SumarHoras(asignatura: Asignatura, asignaturas: ArrayList<Asignatura>,
                   horasSumar: String){
        var nuevoHorasSumar = 1
        var textoUltAccionAct = ""
        var textoResumenAct = ""

        if(!"".equals(horasSumar)){
            nuevoHorasSumar = horasSumar.toInt()
        }

        if(nuevoHorasSumar > 0){
            asignatura.recuentoHoras += nuevoHorasSumar
            textoUltAccionAct = "Se han añadido $nuevoHorasSumar horas a la asignatura ${asignatura.nombre} con precio ${asignatura.precioHora} €."
        }else if(nuevoHorasSumar == 0){
            textoUltAccionAct = "No se ha sumado ningún valor"
        }

        for(asig in asignaturas){
            if(asig.recuentoHoras > 0){
                textoResumenAct += "Asig: ${asig.nombre} precio hora ${asig.precioHora} € total horas: ${asig.recuentoHoras}\n"
            }
        }

        _uiState.update { actualizarTexto -> actualizarTexto.copy(
            textoUltAccion = textoUltAccionAct,
            textoResumen = textoResumenAct
        ) }
    }

    fun RestarHoras(asignatura: Asignatura, asignaturas: ArrayList<Asignatura>,
                    horasRestar: String){
        var nuevoHorasRestar = 1
        var textoUltAccionAct  = ""
        var textoResumenAct = ""

        if(!"".equals(horasRestar)){
            nuevoHorasRestar = horasRestar.toInt()
        }

        if(nuevoHorasRestar > 0 && (nuevoHorasRestar < asignatura.recuentoHoras || nuevoHorasRestar == asignatura.recuentoHoras)){
            asignatura.recuentoHoras -= nuevoHorasRestar
            textoUltAccionAct = "Se han restado $nuevoHorasRestar horas a la asignatura ${asignatura.nombre} con precio ${asignatura.precioHora} €."
        }else if(nuevoHorasRestar > 0 && nuevoHorasRestar > asignatura.recuentoHoras){
            var recuentoHorasProv = asignatura.recuentoHoras
            asignatura.recuentoHoras = 0
            textoUltAccionAct = "Se han restado $recuentoHorasProv horas a la asignatura ${asignatura.nombre} con precio ${asignatura.precioHora} €."
        }else if(nuevoHorasRestar == 0){
            textoUltAccionAct = "No se ha restado ningún valor"
        }

        for(asig in asignaturas){
            if(asig.recuentoHoras > 0){
                textoResumenAct += "Asig: ${asig.nombre} precio hora ${asig.precioHora} € total horas: ${asig.recuentoHoras}\n"
            }
        }
        _uiState.update { actualizarTexto -> actualizarTexto.copy(
            textoUltAccion = textoUltAccionAct,
            textoResumen = textoResumenAct
        ) }

    }
}