package com.example.catagenttracker

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.catagenttracker.ui.theme.CatAgentTrackerTheme
import com.example.catagenttracker.worker.RouteTrackingWorker
import com.example.catagenttracker.worker.RouteTrackingWorker.Companion.DATA_KEY_CAT_AGENT_ID
import com.example.catagenttracker.worker.RouteTrackingWorker.Companion.DATA_KEY_SECONDS_LEFT
import java.util.UUID

class MainActivity : ComponentActivity() {
    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            var trackingWorkId: UUID? by remember {
                mutableStateOf(null)
            }

            fun cancelTracking() {
                trackingWorkId?.let(workManager::cancelWorkById)
            }

            val requestPermissionLauncher =
                rememberLauncherForActivityResult(
                    contract = RequestPermission(),
                    onResult = { hasPermission ->
                        trackingWorkId = startTracking("Agent007")
                    }
                )

            LaunchedEffect(key1 = trackingWorkId) {
                trackingWorkId?.let { collectWorkInfo(it) }
            }

            CatAgentTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Button(
                            onClick = {
                                if (isNotificationPermitted()) {
                                    trackingWorkId = startTracking("Agent007")
                                } else {
                                    requestPermissionLauncher
                                        .launch(POST_NOTIFICATIONS)
                                }
                            },
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            Text(text = "Start")
                        }
                        Button(
                            onClick = {
                                cancelTracking()
                            },
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
    }

    private fun startTracking(catAgentId: String): UUID {
        val catTrackingRequest =
            OneTimeWorkRequestBuilder<RouteTrackingWorker>()
                .setInputData(
                    workDataOf(DATA_KEY_CAT_AGENT_ID to catAgentId)
                ).setExpedited(
                    RUN_AS_NON_EXPEDITED_WORK_REQUEST
                ).build()
        workManager.enqueue(catTrackingRequest)
        return catTrackingRequest.id
    }

    private suspend fun collectWorkInfo(
        requestId: UUID
    ) {
        workManager.getWorkInfoByIdFlow(requestId)
            .collect { info ->
                if (info.state == WorkInfo.State.SUCCEEDED) {
                    Toast.makeText(
                        this, "Agent arrived!", LENGTH_SHORT
                    ).show()
                } else if (info.state == WorkInfo.State.CANCELLED) {
                    Toast.makeText(
                        this, "Deployment cancelled!", LENGTH_SHORT
                    ).show()
                } else if (info.progress != Data.EMPTY) {
                    val catAgentId = info.progress
                        .getString(DATA_KEY_CAT_AGENT_ID)
                    val timeSeconds = info.progress
                        .getInt(DATA_KEY_SECONDS_LEFT, 0)
                    Toast.makeText(
                        this,
                        "$catAgentId arrives in $timeSeconds seconds",
                        LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun isNotificationPermitted(): Boolean = if (
        Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU
    ) {
        ContextCompat.checkSelfPermission(
            this, POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
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
    CatAgentTrackerTheme {
        Greeting("Android")
    }
}
