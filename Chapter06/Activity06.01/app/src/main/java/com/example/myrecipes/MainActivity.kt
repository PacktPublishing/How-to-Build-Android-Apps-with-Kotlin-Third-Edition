package com.example.myrecipes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrecipes.model.Flavor
import com.example.myrecipes.model.ListItemUiModel
import com.example.myrecipes.model.RecipeUiModel
import com.example.myrecipes.ui.theme.MyRecipesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val t = shouldShowRequestPermissionRationale("")
        enableEdgeToEdge()
        setContent {
            MyRecipesTheme {
                val listItems = remember {
                    mutableStateListOf<ListItemUiModel>(
                        ListItemUiModel.Title("Savory Recipes", Flavor.SAVORY),
                        ListItemUiModel.Title("Sweet Recipes", Flavor.SWEET)
                    )
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    HomeScreen(
                        listItems = listItems,
                        onAddRecipeClick = { flavor, title, description ->
                            val flavorTitleIndex = listItems.indexOfFirst { item ->
                                item is ListItemUiModel.Title && item.flavor == flavor
                            }
                            listItems.add(
                                flavorTitleIndex + 1,
                                ListItemUiModel.Recipe(
                                    RecipeUiModel(
                                        title = title,
                                        description = description
                                    )
                                )
                            )
                        },
                        onRecipeClick = { index ->
                            val listItem = listItems[index]
                            if (listItem is ListItemUiModel.Recipe) {
                                Toast.makeText(
                                    context,
                                    listItem.recipe.description,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        onRecipeSwipe = listItems::removeAt,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    listItems: List<ListItemUiModel>,
    modifier: Modifier = Modifier,
    onAddRecipeClick: (Flavor, title: String, description: String) -> Unit = { _, _, _ -> },
    onRecipeClick: (Int) -> Unit = {},
    onRecipeSwipe: (Int) -> Unit = {}
) {
    var recipeTitle by remember { mutableStateOf("") }
    var recipeDescription by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(listItems.size) { index ->
                when (val listItem = listItems[index]) {
                    is ListItemUiModel.Title -> {
                        Text(
                            text = listItem.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    is ListItemUiModel.Recipe -> {
                        Recipe(
                            recipe = listItem.recipe,
                            onClick = { onRecipeClick(index) },
                            onSwipe = { onRecipeSwipe(index) }
                        )
                    }
                }
            }
        }
        TextField(
            value = recipeTitle,
            singleLine = true,
            onValueChange = { recipeTitle = it },
            label = { Text("Recipe Title") },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        )
        TextField(
            value = recipeDescription,
            onValueChange = { recipeDescription = it },
            label = { Text("Recipe Description") },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    onAddRecipeClick(Flavor.SAVORY, recipeTitle, recipeDescription)
                    recipeTitle = ""
                    recipeDescription = ""
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Add Savory"
                )
            }
            Button(
                onClick = {
                    onAddRecipeClick(Flavor.SWEET, recipeTitle, recipeDescription)
                    recipeTitle = ""
                    recipeDescription = ""
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(text = "Add Sweet")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyRecipesTheme {
        HomeScreen(
            listOf(
                ListItemUiModel.Title("Savory Recipes", Flavor.SAVORY),
                ListItemUiModel.Title("Sweet Recipes", Flavor.SWEET)
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}
