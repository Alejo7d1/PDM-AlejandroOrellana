package com.agarcia.pdm_course_2026

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.agarcia.pdm_course_2026.movieapp.MovieApp
import com.agarcia.pdm_course_2026.ui.theme.PDM_Course_2026Theme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate Called")
    enableEdgeToEdge()
    setContent {
      PDM_Course_2026Theme {
        MovieApp()
      }
    }
  }

  override fun onStart() {
    super.onStart()
    Log.d(TAG, "onStart Called")
  }

  override fun onResume() {
    super.onResume()
    Log.d(TAG, "onResume Called")
  }

  override fun onRestart() {
    super.onRestart()
    Log.d(TAG, "onRestart Called")
  }

  override fun onPause() {
    super.onPause()
    Log.d(TAG, "onPause Called")
  }

  override fun onStop() {
    super.onStop()
    Log.d(TAG, "onStop Called")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy Called")
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hola algo mas $name!",
    modifier = modifier,

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  PDM_Course_2026Theme {
    Greeting("Android")
  }
}