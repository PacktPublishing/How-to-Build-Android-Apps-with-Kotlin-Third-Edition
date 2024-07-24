package com.example.whereismycar

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.lifecycleScope
import com.example.whereismycar.ui.theme.WhereIsMyCarTheme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

class MainActivity : ComponentActivity() {
    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

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

            var currentCarLocation by remember {
                mutableStateOf(getSavedLocation() ?: LatLng(0.0, 0.0))
            }

            fun setCarLocationToUserLocation() {
                val cancellationTokenSource = CancellationTokenSource()
                lifecycleScope.launch @SuppressLint("MissingPermission") {
                    suspendCancellableCoroutine { continuation ->
                        fusedLocationClient.getCurrentLocation(
                            PRIORITY_HIGH_ACCURACY,
                            cancellationTokenSource.token
                        ).addOnSuccessListener { location: Location? ->
                            if (location != null) {
                                val locationLatLng = LatLng(location.latitude, location.longitude)
                                currentCarLocation = locationLatLng
                                saveLocation(locationLatLng)
                            }
                        }
                        continuation.invokeOnCancellation {
                            cancellationTokenSource.cancel()
                        }
                    }
                }
            }

            val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    locationPermissionsGranted = permissions.values.all { it }
                    if (locationPermissionsGranted) {
                        setCarLocationToUserLocation()
                    } else {
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

            WhereIsMyCarTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { innerPadding ->
                    val cameraPositionState = rememberSaveable(
                        currentCarLocation,
                        saver = CameraPositionState.Saver
                    ) {
                        CameraPositionState(
                            position = CameraPosition
                                .fromLatLngZoom(currentCarLocation, 10f)
                        )
                    }

                    val markerState = rememberSaveable(
                        currentCarLocation,
                        saver = MarkerState.Saver
                    ) {
                        MarkerState(position = currentCarLocation)
                    }

                    GoogleMap(
                        modifier = Modifier.padding(innerPadding),
                        cameraPositionState = cameraPositionState,
                        onMapClick = { latLng ->
                            markerState.position = latLng
                        }
                    ) {
                        val carIcon by remember {
                            mutableStateOf(
                                getBitmapDescriptorFromVector(
                                    R.drawable.car_marker
                                )
                            )
                        }

                        Marker(
                            state = markerState,
                            title = "My Car",
                            icon = carIcon
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                            onClick = {
                                if (locationPermissionsGranted) {
                                    setCarLocationToUserLocation()
                                } else {
                                    shouldShowLocationPermissionRationale =
                                        shouldShowLocationPermissionRationale()
                                }
                                if (!locationPermissionsGranted &&
                                    !shouldShowLocationPermissionRationale
                                ) {
                                    requestLocationPermission()
                                }
                            }
                        ) {
                            Text(text = "I'm parked here")
                        }
                    }

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

    private fun getBitmapDescriptorFromVector(
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor? = ContextCompat.getDrawable(
        this,
        vectorDrawableResourceId
    )?.let { vectorDrawable ->
        vectorDrawable.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val drawableWithTint = DrawableCompat.wrap(vectorDrawable)
        DrawableCompat.setTint(drawableWithTint, Color.DKGRAY)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawableWithTint.draw(canvas)
        BitmapDescriptorFactory.fromBitmap(bitmap)
            .also { bitmap.recycle() }
    }

    private fun saveLocation(latLng: LatLng) = getPreferences(MODE_PRIVATE)?.edit()?.apply {
        putString("latitude", latLng.latitude.toString())
        putString("longitude", latLng.longitude.toString())
        apply()
    }

    private fun getSavedLocation(): LatLng? {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val latitude = sharedPreferences
            .getString("latitude", null)
            ?.toDoubleOrNull() ?: return null
        val longitude = sharedPreferences
            .getString("longitude", null)
            ?.toDoubleOrNull() ?: return null
        return LatLng(latitude, longitude)
    }
}
