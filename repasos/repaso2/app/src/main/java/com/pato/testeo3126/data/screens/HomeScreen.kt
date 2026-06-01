package com.pato.testeo3126.data.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pato.testeo3126.R
import com.pato.testeo3126.data.model.Note
import com.pato.testeo3126.data.remote.KtorClient
import com.pato.testeo3126.data.repositories.NoteApiRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun HomeScreen(
    onNavigateToNotes: () -> Unit,
    onNavigateToAdd: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ApiNotes",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF37474F),
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Button(
            onClick = onNavigateToNotes,
            modifier = Modifier.size(width = 200.dp, height = 150.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3E5FC), contentColor = Color.DarkGray),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.all_icon),
                    contentDescription = "Ver notas",
                    modifier = Modifier.size(70.dp)
                )
                Text("Mis Notas", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNavigateToAdd,
            modifier = Modifier.size(width = 200.dp, height = 150.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF8BBD0), contentColor = Color.DarkGray),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "Añadir nota",
                    modifier = Modifier.size(70.dp)
                )
                Text("Nueva Nota", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AddScreen(
    onBack: () -> Unit
){
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val repository = remember { NoteApiRepository(KtorClient.client) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nueva Nota", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        if (error != null) {
            Text(text = error!!, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Cuerpo") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Autor") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Button(
            onClick = {
                scope.launch {
                    val result = repository.postNote(title, body, author)
                    if (result.isSuccess) {
                        onBack()
                    } else {
                        error = "Error: ${result.exceptionOrNull()?.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC8E6C9), contentColor = Color.DarkGray)
        ) {
            Text("Enviar", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun NotesScreen(onNoteClick: (Int) -> Unit) {
    var notes by remember { mutableStateOf(listOf<Note>()) }
    var error by remember { mutableStateOf<String?>(null) }
    val repository = remember { NoteApiRepository(KtorClient.client) }

    LaunchedEffect(Unit) {
        try {
            notes = repository.getNotes()
        } catch (e: Exception) {
            error = "Error al cargar notas: ${e.message}"
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Text(
            "Mis Notas",
            modifier = Modifier.padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        if (error != null) {
            Text(
                text = error!!,
                color = Color.Red,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(notes) { note ->
                PostItNote(note = note, onClick = { onNoteClick(note.id) })
            }
        }
    }
}

@Composable
fun PostItNote(note: Note, onClick: () -> Unit) {
    val postItColors = listOf(
        Color(0xFFFFF9C4), // Yellow
        Color(0xFFFFCCBC), // Orange/Peach
        Color(0xFFC8E6C9), // Green
        Color(0xFFB3E5FC), // Blue
        Color(0xFFF8BBD0)  // Pink
    )
    val randomColor = remember { postItColors[Random.nextInt(postItColors.size)] }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f) 
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = randomColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                )
                Text(
                    text = note.body,
                    fontSize = 14.sp,
                    maxLines = 6,
                    lineHeight = 18.sp,
                )
            }
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Por: ${note.author}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive
                )
                Text(text = note.date, fontSize = 9.sp, color = Color.DarkGray)
            }
        }
    }
}
