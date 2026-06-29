package com.agarcia.pdm_course_2026.movieapp.screens.MovieDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.agarcia.pdm_course_2026.clase130426.AppScaffold
import com.agarcia.pdm_course_2026.movieapp.model.Movie
import kotlinx.coroutines.launch

private val genreNames = mapOf(
  28 to "Acción",
  12 to "Aventura",
  16 to "Animación",
  35 to "Comedia",
  80 to "Crimen",
  18 to "Drama",
  10751 to "Familia",
  14 to "Fantasía",
  27 to "Terror",
  9648 to "Misterio",
  10749 to "Romance",
  878 to "Ciencia ficción",
  53 to "Suspense",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBottomSheet(
  onSave: (title: String, body: String) -> Unit,
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState()
  var title by rememberSaveable { mutableStateOf("") }
  var body by rememberSaveable { mutableStateOf("") }

  val isValid = title.isNotBlank() && body.isNotBlank()

  ModalBottomSheet(
    sheetState = sheetState,
    onDismissRequest = onDismiss
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .padding(bottom = 32.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text(
        text = "Deja tu comentario",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
      )
      OutlinedTextField(
        value = title,
        onValueChange = { title = it },
        label = { Text("Título") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
      )
      OutlinedTextField(
        value = body,
        onValueChange = { body = it },
        label = { Text("Comentario") },
        modifier = Modifier
          .fillMaxWidth()
          .height(140.dp),
        maxLines = 5
      )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        TextButton(onClick = onDismiss) {
          Text("Cancelar")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
          onClick = {
            if (isValid) {
              onSave(title, body)
              onDismiss()
            }
          },
          enabled = isValid
        ) {
          Text("Guardar")
        }
      }
    }
  }
}

@Composable
fun MovieDetailScreenV2(
  movieId: Int,
  navigateBack: () -> Unit,
  viewModel: MovieDetailViewModel = viewModel()
) {
  val movie by viewModel.movie.collectAsState()
  val loading by viewModel.loading.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()
  var showCommentSheet by rememberSaveable { mutableStateOf(false) }

  LaunchedEffect(movieId) {
    viewModel.loadMovieById(movieId)
  }

  if (loading) {
    AppScaffold(title = "Movies") { padding ->
      CircularProgressIndicator(modifier = Modifier.padding(padding))
    }
    return
  }

  AppScaffold(
    title = movie?.title ?: "Detail",
    navigationIcon = {
      IconButton(onClick = navigateBack) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back"
        )
      }
    },
    snackbarHostState = snackbarHostState,
    onFabClick = {
      scope.launch {
        snackbarHostState.showSnackbar("Trailer próximamente en YouTube")
      }
    },
    fabIcon = {
      Icon(
        imageVector = Icons.Filled.PlayArrow,
        contentDescription = "Reproducir trailer"
      )
    }
  ) { padding ->
    movie?.let {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding)
          .verticalScroll(rememberScrollState())
      ) {
        HeroHeader(movie = it)
        Spacer(modifier = Modifier.height(16.dp))
        StatsRow(movie = it)
        if (it.genreIds.isNotEmpty()) {
          Spacer(modifier = Modifier.height(16.dp))
          GenresRow(genreIds = it.genreIds)
        }
        Spacer(modifier = Modifier.height(20.dp))
        SectionDivider()
        Spacer(modifier = Modifier.height(20.dp))
        SynopsisSection(overview = it.overview)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
          onClick = { showCommentSheet = true },
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
          Text("Dejar un comentario")
        }
        Spacer(modifier = Modifier.height(24.dp))
      }
    }

    if (showCommentSheet) {
      CommentBottomSheet(
        onSave = { title, body ->
          // viewModel.createPost(title, body)
        },
        onDismiss = { showCommentSheet = false }
      )
    }
  }
}

@Composable
private fun HeroHeader(movie: Movie) {
  Box(modifier = Modifier.fillMaxWidth()) {
    AsyncImage(
      model = movie.backdropUrl,
      contentDescription = movie.title,
      modifier = Modifier
        .fillMaxWidth()
        .height(260.dp),
      contentScale = ContentScale.Crop
    )
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(260.dp)
        .background(
          Brush.verticalGradient(
            colorStops = arrayOf(
              0.0f to Color.Black.copy(alpha = 0.35f),
              0.35f to Color.Transparent,
              1.0f to Color.Black.copy(alpha = 0.9f)
            )
          )
        )
    )
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomStart)
        .padding(16.dp),
      verticalAlignment = Alignment.Bottom
    ) {
      ElevatedCard(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
      ) {
        AsyncImage(
          model = movie.posterUrl,
          contentDescription = movie.title,
          modifier = Modifier.size(width = 110.dp, height = 165.dp),
          contentScale = ContentScale.Crop
        )
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(
          text = movie.title,
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
        if (movie.originalTitle != movie.title) {
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = movie.originalTitle,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.75f)
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
        RatingBadge(rating = movie.voteAverage, votes = movie.voteCount)
      }
    }
  }
}

@Composable
private fun RatingBadge(rating: Double, votes: Int) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .clip(RoundedCornerShape(50))
      .background(Color.Black.copy(alpha = 0.55f))
      .padding(horizontal = 10.dp, vertical = 6.dp)
  ) {
    Icon(
      imageVector = Icons.Filled.Star,
      contentDescription = null,
      tint = Color(0xFFFFC107),
      modifier = Modifier.size(16.dp)
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = "%.1f".format(rating),
      style = MaterialTheme.typography.labelLarge,
      fontWeight = FontWeight.Bold,
      color = Color.White
    )
    Text(
      text = "  ·  $votes votos",
      style = MaterialTheme.typography.labelSmall,
      color = Color.White.copy(alpha = 0.8f)
    )
  }
}

@Composable
private fun StatsRow(movie: Movie) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    StatTile(
      icon = Icons.Filled.CalendarMonth,
      value = formatReleaseDate(movie.releaseDate),
      label = "Estreno",
      modifier = Modifier.weight(1f)
    )
    StatTile(
      icon = Icons.AutoMirrored.Filled.TrendingUp,
      value = formatPopularity(movie.popularity),
      label = "Popularidad",
      modifier = Modifier.weight(1f)
    )
    StatTile(
      icon = Icons.Filled.Language,
      value = movie.originalLanguage.uppercase(),
      label = "Idioma",
      modifier = Modifier.weight(1f)
    )
  }
}

@Composable
private fun StatTile(
  icon: ImageVector,
  value: String,
  label: String,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp, horizontal = 8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        maxLines = 1
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
private fun SectionDivider() {
  HorizontalDivider(
    modifier = Modifier.padding(horizontal = 16.dp),
    color = MaterialTheme.colorScheme.outlineVariant
  )
}

private val months = listOf(
  "ENE", "FEB", "MAR", "ABR", "MAY", "JUN",
  "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"
)

private fun formatReleaseDate(iso: String): String {
  val parts = iso.split("-")
  if (parts.size != 3) return iso
  val year = parts[0]
  val monthIdx = parts[1].toIntOrNull()?.minus(1) ?: return iso
  val day = parts[2].trimStart('0')
  val month = months.getOrNull(monthIdx) ?: return iso
  return "$day $month $year"
}

private fun formatPopularity(value: Double): String = when {
  value >= 1_000_000 -> "%.1fM".format(value / 1_000_000)
  value >= 1_000 -> "%.1fK".format(value / 1_000)
  else -> "%.0f".format(value)
}

@Composable
private fun GenrePill(name: String) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(50))
      .background(
        Brush.horizontalGradient(
          colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.18f)
          )
        )
      )
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
        shape = RoundedCornerShape(50)
      )
      .padding(horizontal = 14.dp, vertical = 8.dp)
  ) {
    Text(
      text = name,
      style = MaterialTheme.typography.labelMedium,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenresRow(genreIds: List<Int>) {
  FlowRow(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    genreIds.mapNotNull { genreNames[it] }.forEach { name ->
      GenrePill(name = name)
    }
  }
}

@Composable
private fun SynopsisSection(overview: String) {
  Column(modifier = Modifier.padding(horizontal = 16.dp)) {
    Text(
      text = "Sinopsis",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Card(
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
      )
    ) {
      Text(
        text = overview.ifBlank { "Sin sinopsis disponible." },
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(16.dp)
      )
    }
  }
}
