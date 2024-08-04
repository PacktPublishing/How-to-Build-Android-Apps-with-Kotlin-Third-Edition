package com.example.popularmovies.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.popularmovies.LocalMargin
import com.example.popularmovies.model.Movie
import com.example.popularmovies.ui.theme.PopularMoviesTheme

@Composable
fun MovieItemView(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClick: (Movie) -> Unit
) {
    LaunchedEffect(movie) {
        Log.d("MovieItemView", "Movie: ${movie.title}")
    }
    Column(
        modifier = modifier.clickable { onClick(movie) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
            contentDescription = movie.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(240.dp)
        )
        Text(
            text = movie.title,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = LocalMargin.current.margin),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemViewPreview() {
    PopularMoviesTheme {
        MovieItemView(
            Movie(
                title = "Movie Title",
                posterPath = "/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
            )
        ) {}
    }
}