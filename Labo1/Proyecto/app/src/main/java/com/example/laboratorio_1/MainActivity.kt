package com.example.laboratorio_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.laboratorio_1.ejercicios.Calculadora
import com.example.laboratorio_1.ejercicios.Computadora
import com.example.laboratorio_1.ejercicios.Estudiante
import com.example.laboratorio_1.ui.theme.Laboratorio_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio_1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Alejandro",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    // Ejercicio 1
    val pc = Computadora(
        16,
        1024,
        "Windows",
        mutableListOf("Notion 2026", "Facebook 2024", "VSCode 2026")
    )

    //Ejercicio 2
    val calc = Calculadora("Casio", 5, 20.0)
    val result = calc.multiply(5, 3)

    val programas2026 = pc.programs2026()

    //Ejercicio 3
    val ciclo01 = listOf(
        Estudiante("Juan Carlos bodoque", "00004523", "Programación de Dispositivos Móviles"),
        Estudiante("Padalustro Ovo", "00123222", "Programación de Dispositivos Móviles"),
        Estudiante("Fabritcio Montacargas", "00463723", "Programación de Dispositivos Móviles"),
        Estudiante("Paquin Malosvientos", "00336623", "Análisis numérico"),
        Estudiante("Flaco Delgado Salgado", "00463723", "Programación de Dispositivos Móviles")
    )

    val pdm = ciclo01.filter {
        it.asignatura == "Programación de Dispositivos Móviles"
    }
    val nombres = ciclo01.joinToString { it.nombre }
    val nombresPDM = pdm.joinToString { it.nombre }


    //mostrar las bainas
    Text(
        text = """
            Hola $name
            
            **********Ejercicio 1********
            Info Sistema:
            RAM instalada: ${pc.ram} GB
            Almacenamiento: ${pc.almacenamiento} GB
            SO: ${pc.sistemaOperativo}
            Programas del 2026: $programas2026
            
            **********Ejercicio 2********
            Calculadora ${calc.marca}
            vida estimada: ${calc.vidaUtil} años
            Operación: Multiplicar
            Resultado: $result
            
            **********Ejercicio 3********
            Estudiantes del ciclo01: 
            $nombres
            Estudiante de PDM: 
            $nombresPDM
            
            
            """,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Laboratorio_1Theme {
        Greeting("Android")
    }
}