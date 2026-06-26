package com.pato.labo3

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pato.labo3.ui.theme.Labo3Theme
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object NameList

@Serializable
object SensorInfo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Labo3Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(
                onNavigateToNames = { navController.navigate(NameList) },
                onNavigateToSensors = { navController.navigate(SensorInfo) }
            )
        }
        composable<NameList> {
            NameListScreen(onBack = { navController.popBackStack() })
        }
        composable<SensorInfo> {
            SensorScreen(onBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun HomeScreen(onNavigateToNames: () -> Unit, onNavigateToSensors: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            Button(
                onClick = onNavigateToNames,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Ver la lista de nombres", fontSize = 18.sp)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onNavigateToSensors,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Ver información de sensores", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun NameListScreen(onBack: () -> Unit) {
    val names = listOf(
        "Alejandro Orellana", 
        "Juan Pérez", 
        "María García", 
        "Carlos Rodríguez", 
        "Ana Martínez",
        "Luis Hernández",
        "Elena Gómez",
        "Roberto Díaz"
    )
    
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Button(onClick = onBack, modifier = Modifier.padding(bottom = 16.dp)) {
                Text("Volver")
            }
            
            Text(
                text = "Listado de Nombres",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(names) { name ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = name,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SensorScreen(onBack: () -> Unit) {
    // Usamos el hook useSensor proporcionado en la guía
    val accelerometerValues = useSensor(Sensor.TYPE_ACCELEROMETER)
    val lightValues = useSensor(Sensor.TYPE_LIGHT)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onBack, modifier = Modifier.align(Alignment.Start)) {
                Text("Volver")
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Información de Sensores",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Sección Acelerómetro
            SensorCard(
                title = "Acelerómetro",
                values = if (accelerometerValues.isNotEmpty()) {
                    listOf(
                        "Eje X: ${"%.2f".format(accelerometerValues[0])}",
                        "Eje Y: ${"%.2f".format(accelerometerValues[1])}",
                        "Eje Z: ${"%.2f".format(accelerometerValues[2])}"
                    )
                } else null
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Sección Luz Ambiental
            SensorCard(
                title = "Luz Ambiental",
                values = if (lightValues.isNotEmpty()) {
                    listOf("Intensidad: ${"%.2f".format(lightValues[0])} lx")
                } else null
            )
            
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun SensorCard(title: String, values: List<String>?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            if (values != null) {
                values.forEach { value ->
                    Text(text = value, style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                Text(text = "Sensor no disponible", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun useSensor(sensorType: Int): List<Float> {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val sensor = sensorManager.getDefaultSensor(sensorType) ?: return emptyList()
    var sensorValues by remember { mutableStateOf(listOf(0f, 0f, 0f)) }

    DisposableEffect(sensorType) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.values?.let {
                    sensorValues = it.toList()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    return sensorValues
}
