package com.example.catagentdeployer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.catagentdeployer.ui.theme.CatAgentDeployerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var locationPermissionsGranted by remember {
                mutableStateOf(areLocationPermissionsGranted())
            }

            var shouldShowLocationPermissionRationale by remember {
                mutableStateOf(false)
            }

            val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    locationPermissionsGranted = permissions.values.all { it }
                    if (!locationPermissionsGranted) {
                        shouldShowLocationPermissionRationale =
                            shouldShowLocationPermissionRationale()
                    }
                })

            fun requestLocationPermission() {
                requestLocationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }

//            val lifecycleOwner = LocalLifecycleOwner.current
//            DisposableEffect(
//                key1 = lifecycleOwner,
//                effect = {
//                    val observer = LifecycleEventObserver { _, event ->
//                        if (event == Lifecycle.Event.ON_START) {
//                            if (!locationPermissionsGranted) {
//                                shouldShowLocationPermissionRationale =
//                                    shouldShowRequestLocationPermissionRationale()
//                            }
//                            if (!locationPermissionsGranted &&
//                                !shouldShowLocationPermissionRationale
//                            ) {
//                                requestLocationPermission()
//                            }
//                        }
//                    }
//                    lifecycleOwner.lifecycle.addObserver(observer)
//                    onDispose {
//                        lifecycleOwner.lifecycle.removeObserver(observer)
//                    }
//                }
//            )

            CatAgentDeployerTheme {

                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { innerPadding ->
                    Button(
                        onClick = {
                            if (!locationPermissionsGranted) {
                                shouldShowLocationPermissionRationale =
                                    shouldShowLocationPermissionRationale()
                            }
                            if (!locationPermissionsGranted &&
                                !shouldShowLocationPermissionRationale
                            ) {
                                requestLocationPermission()
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Text(text = "Request permission")
                    }
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier
//                            .padding(innerPadding)
//                    )

                    val scope = rememberCoroutineScope()

                    LaunchedEffect(
                        key1 = shouldShowLocationPermissionRationale,
                        block = {
                            if (shouldShowLocationPermissionRationale) {
                                scope.launch {
                                    val userAction = snackbarHostState.showSnackbar(
                                        message = "The app will not work without knowing your precise location",
                                        actionLabel = "Approve",
                                        duration = SnackbarDuration.Indefinite,
                                        withDismissAction = true
                                    )
                                    when (userAction) {
                                        SnackbarResult.ActionPerformed -> {
                                            requestLocationPermission()
                                        }

                                        SnackbarResult.Dismissed -> {}
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private fun shouldShowLocationPermissionRationale() =
        shouldShowRequestPermissionRationale(
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) || shouldShowRequestPermissionRationale(
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    private fun areLocationPermissionsGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CatAgentDeployerTheme {
        Greeting("Android")
    }
}
