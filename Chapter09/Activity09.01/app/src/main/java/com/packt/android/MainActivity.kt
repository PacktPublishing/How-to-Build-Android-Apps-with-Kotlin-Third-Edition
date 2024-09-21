package com.packt.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import com.packt.android.ui.theme.Activity0901Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Activity0901Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ItemList(
                        modifier = Modifier.padding(innerPadding),
                        randomNumberGenerator = (application as RandomApplication).randomNumberGenerator
                    )
                }
            }
        }
    }
}

@Composable
fun ItemList(
    modifier: Modifier,
    randomNumberGenerator: RandomNumberGenerator
) {
    var clickedItem by remember { mutableStateOf(0) }
    var itemCount by remember { mutableStateOf(0) }
    ItemListScreen(
        modifier = modifier,
        itemCount = itemCount,
        clickedItem = clickedItem,
        onPressMeClicked = {
            itemCount = randomNumberGenerator.generateNumber()
        },
        onItemClicked = {
            clickedItem = it
        }
    )
}

@Composable
fun ItemListScreen(
    modifier: Modifier,
    itemCount: Int = 0,
    clickedItem: Int = 0,
    onPressMeClicked: () -> Unit,
    onItemClicked: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Button(onClick = onPressMeClicked) {
                Text(text = stringResource(id = R.string.press_me))
            }
            Text(
                text = stringResource(id = R.string.clicked_item_x, clickedItem),
                modifier = modifier
            )
        }
        items(itemCount) {
            val row = it + 1
            ClickableText(text = AnnotatedString(stringResource(id = R.string.item_x, row))) {
                onItemClicked(row)
            }
        }
    }
}

