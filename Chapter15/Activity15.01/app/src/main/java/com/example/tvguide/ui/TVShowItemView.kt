package com.example.tvguide.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tvguide.R
import com.example.tvguide.model.TVShow
import com.example.tvguide.ui.theme.TVGuideTheme

@Composable
fun TVShowItemView(
    tvShow: TVShow,
    modifier: Modifier = Modifier,
    onClick: (TVShow) -> Unit
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .clickable { onClick(tvShow) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${tvShow.posterPath}",
            contentDescription = tvShow.name,
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            modifier = Modifier.height(240.dp)
        )
        Text(
            text = tvShow.name,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 16.dp),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TVShowItemViewPreview() {
    TVGuideTheme {
        TVShowItemView(
            TVShow(
                name = "TV Show Title",
                posterPath = "/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
            )
        ) {}
    }
}