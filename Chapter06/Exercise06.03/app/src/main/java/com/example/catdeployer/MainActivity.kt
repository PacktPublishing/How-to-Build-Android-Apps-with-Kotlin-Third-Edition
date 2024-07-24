package com.example.catdeployer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.catdeployer.model.CatUiModel
import com.example.catdeployer.model.Gender
import com.example.catdeployer.ui.theme.CatDeployerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatDeployerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val cats = listOf(
                        CatUiModel(
                            Gender.MALE,
                            "Fred",
                            "Silent and deadly",
                            "https://24.media.tumblr.com/tumblr_lsln7s1Z8f1qasbyxo1_250.jpg"
                        ),
                        CatUiModel(
                            Gender.FEMALE,
                            "Wilma",
                            "Cuddly assassin",
                            "https://cdn2.thecatapi.com/images/KJF8fB_20.jpg"
                        ),
                        CatUiModel(
                            Gender.UNKNOWN,
                            "Curious George",
                            "Award winning investigator",
                            "https://cdn2.thecatapi.com/images/vJB8rwfdX.jpg"
                        )
                    )
                    val context = LocalContext.current
                    CatAgents(
                        cats = cats,
                        modifier = Modifier.padding(innerPadding),
                        onCatClick = { catIndex ->
                            Toast.makeText(
                                context,
                                "${cats[catIndex].name} clicked!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CatAgents(
    cats: List<CatUiModel>,
    modifier: Modifier = Modifier,
    onCatClick: (Int) -> Unit = {}
) {
    val columnState = rememberLazyListState()

    LazyColumn(state = columnState, modifier = modifier) {
        items(cats.size) { index ->
            Cat(
                cat = cats[index],
                onClick = {
                    onCatClick(index)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CatDeployerTheme {
        CatAgents(
            cats = listOf(
                CatUiModel(
                    gender = Gender.MALE,
                    name = "Fred",
                    biography = "Silent and deadly",
                    imageUrl = ""
                ),
                CatUiModel(
                    gender = Gender.FEMALE,
                    name = "Wilma",
                    biography = "Cuddly assassin",
                    imageUrl = ""
                ),
                CatUiModel(
                    gender = Gender.UNKNOWN,
                    name = "Curious George",
                    biography = "Award winning investigator",
                    imageUrl = ""
                )
            )
        )
    }
}
