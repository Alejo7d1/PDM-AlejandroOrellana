package com.example.labo2

import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.labo2.ui.theme.Labo2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Labo2Theme {
                mainScreen();
            }
        }
    }
}
@Composable
fun mainScreen(){
    var nombre = remember { mutableStateOf("") }
    val nombresTabla = remember { mutableStateListOf("Andres", "Fabio", "Paco", "Oscar") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        ) {
       TextField(
           value = nombre.value,
           onValueChange = {nombre.value = it}
       )
        Button(
            onClick = {nombresTabla.add(nombre.value)},
            modifier = Modifier
        ) {
            Text("Guardar")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Listado de nombre y posición en la lista")
            Button({}) {
                Text("Limpiar")
            }
        }
        LazyColumn(
            modifier = Modifier
                .border(width = 1.dp, color = Color.Blue, shape = CircleShape)
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            itemsIndexed(nombresTabla) { index, item ->
                Text(item)
                Text(index.toString())
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun prueba(){
    mainScreen()
}