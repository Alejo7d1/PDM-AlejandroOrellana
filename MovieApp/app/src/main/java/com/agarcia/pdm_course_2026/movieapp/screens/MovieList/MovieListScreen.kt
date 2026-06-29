package com.agarcia.pdm_course_2026.movieapp.screens.MovieList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agarcia.pdm_course_2026.clase130426.AppScaffold
import com.agarcia.pdm_course_2026.movieapp.components.MovieItem

@Composable
fun MovieListScreen(
  navigateToDetail: (Int) -> Unit,
  viewModel: MovieListViewModel = viewModel()
) {

  val movies by viewModel.movies.collectAsState()
  val loading by viewModel.loading.collectAsState()
  val error by viewModel.error.collectAsState()
  val refreshing by viewModel.refresh.collectAsState()

  if (loading) {
    AppScaffold(title = "Movies") { padding ->
      CircularProgressIndicator(modifier = Modifier.padding(padding))
    }
    return
  }

  if (error != null) {
    AppScaffold(title = "Movies") { padding ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding)
          .padding(16.dp)
          .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
      ) {
        Text(
          text = "$error"
        )
        Button(onClick = { viewModel.loadMovies() }) {
          Text(
            text = "Reintentar",
            color = Color.Black
          )

        }
      }
    }
    return
  }

  AppScaffold(title = "Movies") { padding ->
    PullToRefreshBox(
      isRefreshing = refreshing,
      onRefresh = { viewModel.refreshMovies() },
      modifier = Modifier.fillMaxSize()
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding)
          .padding(16.dp),
      ) {
        items(movies) { movie ->
          MovieItem(
            movie = movie,
            onClick = { navigateToDetail(movie.id) }
          )
          Spacer(modifier = Modifier.height(12.dp))
        }
      }
    }


  }
}
