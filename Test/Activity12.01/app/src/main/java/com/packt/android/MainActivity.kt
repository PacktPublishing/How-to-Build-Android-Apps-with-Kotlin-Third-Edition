package com.packt.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.packt.android.api.DogService
import com.packt.android.storage.filesystem.FileToUriMapper
import com.packt.android.storage.filesystem.ProviderFileHandler
import com.packt.android.storage.preference.DownloadPreferences
import com.packt.android.storage.repository.DogMapper
import com.packt.android.storage.repository.DogRepositoryImpl
import com.packt.android.storage.repository.DogUi
import com.packt.android.storage.repository.Result
import com.packt.android.storage.room.DogDatabase
import com.packt.android.ui.theme.Activity1201Theme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val downloadService = retrofit.create<DogService>(DogService::class.java)
        val database = Room.databaseBuilder(applicationContext, DogDatabase::class.java, "dog-db")
            .build()
        val dogRepository = DogRepositoryImpl(
            DownloadPreferences(applicationContext),
            ProviderFileHandler(
                applicationContext,
                FileToUriMapper()
            ),
            downloadService,
            database.dogDao(),
            DogMapper()
        )

        setContent {
            Activity1201Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel =
                        viewModel<DogViewModel>(factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return DogViewModel(dogRepository) as T
                            }
                        })
                    Dog(
                        dogViewModel = viewModel, modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun Dog(
    dogViewModel: DogViewModel,
    modifier: Modifier
) {

    when (val result = dogViewModel.state.collectAsState().value) {
        is Result.Success -> {
            DogScreen(
                dogs = result.data,
                onNumberOfResultsClicked = {
                    dogViewModel.saveNumberOfResult(it)
                },
                onRowClicked = {
                    dogViewModel.downloadDog(it)
                },
                modifier = modifier
            )
        }

        is Result.Loading -> {
            Loading(modifier = modifier)
        }

        is Result.Error -> {
            Error(modifier = modifier)
        }
    }
}

@Composable
fun DogScreen(
    dogs: List<DogUi>,
    onNumberOfResultsClicked: (Int) -> Unit,
    onRowClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            var textFieldText by remember {
                mutableStateOf(dogs.size.toString())
            }
            TextField(value = textFieldText,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = {
                    textFieldText = it
                })
            Button(onClick = {
                onNumberOfResultsClicked(textFieldText.toInt())
            }) {
                Text(text = stringResource(id = R.string.click_me))
            }
        }
        items(dogs.size) {
            val index = it
            ClickableText(
                text = AnnotatedString(text = dogs[it].url),
                modifier = Modifier.padding(16.dp)
            ) {
                onRowClicked(dogs[index].url)
            }
        }

    }
}

@Composable
fun Loading(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier.width(64.dp)
    )
}

@Composable
fun Error(modifier: Modifier) {
    Text(
        text = "Something went wrong",
        modifier = modifier
    )
}