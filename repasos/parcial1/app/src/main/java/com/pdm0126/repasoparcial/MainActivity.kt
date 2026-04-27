package com.pdm0126.repasoparcial

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           BasicNavigationWrapper()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ViewContainer(navigateToDetail: (String)-> Unit){
    Scaffold(
        topBar = {Toolbar()},
        content = {AppContent(navigateToDetail)},
        floatingActionButton = {FAB()},
        floatingActionButtonPosition = FabPosition.End
    )
}

@Composable
fun FAB(){ //botón flotante
    val context = LocalContext.current // Atribuye contexto
    FloatingActionButton(onClick = {
        Toast.makeText(context, "POWER", Toast.LENGTH_SHORT).show()
    }){
        Text("P")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Toolbar(){
    TopAppBar(title = {Text(text = "Hola amigos de youtube")}, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Black,
        titleContentColor = Color.White
    ))
}

@Composable
fun AppContent(navigateToDetail: (String) -> Unit) {
    var counter by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(10.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.morra_porra),
            contentDescription = "mona china anime"
        )
        Row(modifier = Modifier.padding(top = 10.dp)) {
            Image(
                modifier = Modifier
                    .clickable(){counter++},
                painter = painterResource(id = R.drawable.fav_ic),
                contentDescription = "like icon"
            )
            Text(text = counter.toString(), color = Color.White, modifier = Modifier.padding(start = 4.dp))

        }
        Text(
            text = "Mangos",
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp, 5.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "con",
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = "Chile", color = Color.White)
            Text(text = "Limón", color = Color.White)
            Text(text = "Sal", color = Color.White)
        }
        Scaffold{ paddingValues ->
            LazyColumn(Modifier.padding(paddingValues)){
                items (30){
                    Box(contentAlignment = Alignment.CenterStart, modifier = Modifier
                        .padding(start = 16.dp)
                        .height(30.dp)
                        .fillMaxWidth()
                        .clickable{
                            navigateToDetail(it.toString())
                        }
                    ){
                        Text("Soy la posición $it")
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(id: String){
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues),contentAlignment = Alignment.Center){
            Text("La ID seleccionada es: $id", fontSize = 25.sp)
        }
    }
}

