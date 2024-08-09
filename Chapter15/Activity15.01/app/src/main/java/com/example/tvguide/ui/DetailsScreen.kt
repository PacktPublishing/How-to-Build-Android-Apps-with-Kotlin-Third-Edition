package com.example.tvguide.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tvguide.R
import com.example.tvguide.ui.theme.TVGuideTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    title: String,
    release: String,
    overview: String,
    image: String,
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = topAppBarColors(MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    IconButton(onClick = { onBackButtonClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        var largeOverview by remember { mutableStateOf(true) }
        var showRelease by remember { mutableStateOf(true) }
        var fullAlpha by remember { mutableStateOf(true) }
        val alpha: Float by animateFloatAsState(if (fullAlpha) 1f else 0.5f, label = "Photo-Alpha")

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = stringResource(id = R.string.tv_show_poster),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .alpha(alpha)
                    .clickable {
                        fullAlpha = fullAlpha.not()
                    }
            )
            Text(
                text = stringResource(id = R.string.tv_show_title, title),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showRelease = showRelease.not()
                    }
            )
            AnimatedVisibility(visible = showRelease) {
                Text(
                    text = stringResource(id = R.string.tv_show_release, release),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = stringResource(id = R.string.tv_show_overview, overview),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .height(if (largeOverview) 240.dp else 40.dp)
                    .clickable {
                        largeOverview = largeOverview.not()
                    },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsViewPreview() {
    TVGuideTheme {
        DetailsScreen(
            title = "TV Show Title",
            release = "2024-05-06",
            overview = "TV Show overview line 1\nLine 2",
            image = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg"
        ) {}
    }
}