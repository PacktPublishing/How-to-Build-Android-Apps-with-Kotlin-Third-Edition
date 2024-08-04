package com.packt.android

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.packt.android.ui.theme.Exercise1204Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Exercise1204Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Media(
                        fileViewModel = viewModel<FileViewModel>(
                            factory = object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                    return FileViewModel(
                                        ProviderFileManager(
                                            applicationContext,
                                            FileHelper(applicationContext),
                                            contentResolver,
                                            MediaContentHelper()
                                        )
                                    ) as T
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
fun Media(
    fileViewModel: FileViewModel,
    modifier: Modifier = Modifier
) {
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        fileViewModel.insertImageToStore()
    }
    val videoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CaptureVideo()) {
        fileViewModel.insertVideoToStore()
    }
    var isPhoto = remember {
        true
    }
    val launchCamera = {
        if (isPhoto) {
            imageLauncher.launch(fileViewModel.generatePhotoUri(System.currentTimeMillis()))
        } else {
            videoLauncher.launch(fileViewModel.generateVideoUri(System.currentTimeMillis()))
        }
    }
    val externalStoragePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchCamera()
        }
    }
    val context = LocalContext.current
    val checkStoragePermissions = {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
            when (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )) {
                PackageManager.PERMISSION_GRANTED -> {
                    launchCamera()
                }
                else -> {
                    externalStoragePermissionLauncher.launch( android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        } else {
            launchCamera()
        }
    }
    MediaScreen(
        onPhotoClicked = {
            isPhoto = true
            checkStoragePermissions()

        },
        onVideoClicked = {
            isPhoto = false
            checkStoragePermissions()
        },
        modifier = modifier
    )
}


@Composable
fun MediaScreen(
    onPhotoClicked: () -> Unit,
    onVideoClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Button(onClick = onPhotoClicked) {
            Text(text = stringResource(id = R.string.photo))
        }
        Button(onClick = onVideoClicked) {
            Text(text = stringResource(id = R.string.video))
        }
    }
}