package com.example.catdeployer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.catdeployer.model.CatUiModel
import com.example.catdeployer.model.Gender
import com.example.catdeployer.model.ListItemUiModel
import com.example.catdeployer.ui.theme.CatDeployerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatDeployerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val listItems = remember {
                        mutableStateListOf(
                            ListItemUiModel.Title("Sleeper Agents"),
                            ListItemUiModel.Cat(
                                CatUiModel(
                                    Gender.MALE,
                                    "Fred",
                                    "Silent and deadly",
                                    "https://24.media.tumblr.com/tumblr_lsln7s1Z8f1qasbyxo1_250.jpg"
                                )
                            ),
                            ListItemUiModel.Cat(
                                CatUiModel(
                                    Gender.FEMALE,
                                    "Wilma",
                                    "Cuddly assassin",
                                    "https://cdn2.thecatapi.com/images/KJF8fB_20.jpg"
                                )
                            ),
                            ListItemUiModel.Title("Active Agents"),
                            ListItemUiModel.Cat(
                                CatUiModel(
                                    Gender.UNKNOWN,
                                    "Curious George",
                                    "Award winning investigator",
                                    "https://cdn2.thecatapi.com/images/vJB8rwfdX.jpg"
                                )
                            )
                        )
                    }
                    val context = LocalContext.current
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxWidth()
                    ) {
                        Button(onClick = {
                            listItems.add(
                                1,
                                ListItemUiModel.Cat(
                                    CatUiModel(
                                        gender = Gender.FEMALE,
                                        name = "Anonymous",
                                        biography = "Unknown",
                                        imageUrl = "https://cdn2.thecatapi.com/images/zJkeHza2K.jpg"
                                    )
                                )
                            )
                        }) {
                            Text(text = "Add Cat")
                        }
                        CatAgents(
                            listItems = listItems,
                            onItemClick = { itemIndex ->
                                val listItem = listItems[itemIndex]
                                if (listItem is ListItemUiModel.Cat) {
                                    Toast.makeText(
                                        context,
                                        "${listItem.cat.name} clicked!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            onItemSwipe = { itemIndex ->
                                listItems.removeAt(itemIndex)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CatAgents(
    listItems: List<ListItemUiModel>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit = {},
    onItemSwipe: (Int) -> Unit = {}
) {
    val columnState = rememberLazyListState()

    LazyColumn(state = columnState, modifier = modifier) {
        items(
            listItems.size,
            key = { index -> listItems[index].id }) { index ->
            when (val item = listItems[index]) {
                is ListItemUiModel.Title -> {
                    Text(item.title)
                }

                is ListItemUiModel.Cat -> {
                    Cat(
                        cat = item.cat,
                        onClick = {
                            onItemClick(index)
                        },
                        onSwipe = {
                            onItemSwipe(index)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CatDeployerTheme {
        CatAgents(
            listItems = listOf(
                ListItemUiModel.Title("Happy Cats"),
                ListItemUiModel.Cat(
                    CatUiModel(
                        Gender.MALE,
                        "Fred",
                        "Silent and deadly",
                        "https://24.media.tumblr.com/tumblr_lsln7s1Z8f1qasbyxo1_250.jpg"
                    )
                ),
                ListItemUiModel.Cat(
                    CatUiModel(
                        Gender.FEMALE,
                        "Wilma",
                        "Cuddly assassin",
                        "https://cdn2.thecatapi.com/images/KJF8fB_20.jpg"
                    )
                ),
                ListItemUiModel.Title("Sad Cats"),
                ListItemUiModel.Cat(
                    CatUiModel(
                        Gender.UNKNOWN,
                        "Curious George",
                        "Award winning investigator",
                        "https://cdn2.thecatapi.com/images/vJB8rwfdX.jpg"
                    )
                )
            )
        )
    }
}
