package com.pato.testeo3126

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pato.testeo3126.data.navegation.MainNavigation
import com.pato.testeo3126.ui.theme.EstudioParcial2_2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EstudioParcial2_2Theme {
                MainNavigation()
            }
        }
    }
}
