package com.packt.android

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.packt.android.ui.theme.Exercise1203Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val assetFileManager = AssetFileManager(assets)
        val providerFileManager = ProviderFileManager(applicationContext, FileToUriMapper())
        setContent {
            Exercise1203Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    File(
                        fileViewModel = viewModel<FileViewModel>(
                            factory = object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                    return FileViewModel(assetFileManager, providerFileManager) as T
                                }
                            }
                        ),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun File(
    fileViewModel: FileViewModel,
    modifier: Modifier
) {
    val safFileName = "CopiedSAF.txt"
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) { uri: Uri? ->
            uri?.let {
                fileViewModel.copyUsingSaf(it)
            }
        }
    FileScreen(
        onFileProviderClicked = { fileViewModel.copyUsingFileProvider() },
        onSafClicked = { launcher.launch(safFileName) },
        modifier
    )
}

@Composable
fun FileScreen(
    onFileProviderClicked: () -> Unit,
    onSafClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Button(onClick = onFileProviderClicked) {
            Text(text = stringResource(id = R.string.file_provider))
        }
        Button(onClick = onSafClicked) {
            Text(text = stringResource(id = R.string.saf))
        }
    }
}

