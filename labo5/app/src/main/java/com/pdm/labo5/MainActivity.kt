package com.pdm.labo5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pdm.labo5.data.AppProvider
import com.pdm.labo5.navegation.AppNavegation
import com.pdm.labo5.ui.theme.LabTheme

class MainActivity : ComponentActivity() {
    val appProvider by lazy { AppProvider(this) }
        
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabTheme {
                AppNavegation(appProvider.provideTaskRepository())
            }
        }
    }
}
