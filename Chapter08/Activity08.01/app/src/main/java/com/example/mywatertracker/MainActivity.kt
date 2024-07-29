package com.example.mywatertracker

import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
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
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.mywatertracker.ui.theme.MyWaterTrackerTheme
import com.example.mywatertracker.worker.WaterConsumptionWorker
import com.example.mywatertracker.worker.WaterConsumptionWorker.Companion.DATA_KEY_WATER_BALANCE
import java.util.UUID

class MainActivity : ComponentActivity() {
    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showNotificationPermissionRationale by remember {
                mutableStateOf(false)
            }

            var waterBalance by remember {
                mutableFloatStateOf(0f)
            }

            var waterConsumptionWorkId: UUID? by remember {
                mutableStateOf(null)
            }

            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = RequestPermission(),
                onResult = { hasPermission ->
                    if (hasPermission) {
                        waterConsumptionWorkId = startConsumption()
                    } else {
                        showNotificationPermissionRationale =
                            shouldShowNotificationPermissionRationale()
                    }
                }
            )

            @SuppressLint("InlinedApi")
            fun requestNotificationsPermission() {
                requestPermissionLauncher.launch(POST_NOTIFICATIONS)
            }

            suspend fun collectWorkInfo(requestId: UUID) {
                workManager.getWorkInfoByIdFlow(requestId)
                    .collect { info ->
                        if (info.state == WorkInfo.State.CANCELLED) {
                            Toast.makeText(
                                this, "Monitoring stopped.", LENGTH_SHORT
                            ).show()
                        } else if (info.progress != Data.EMPTY) {
                            waterBalance =
                                info.progress.getFloat(DATA_KEY_WATER_BALANCE, waterBalance)
                        }
                    }
            }

            LaunchedEffect(key1 = waterConsumptionWorkId) {
                waterConsumptionWorkId?.let { collectWorkInfo(it) }
            }

            MyWaterTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        TextButton(onClick = {
                            if (isNotificationPermitted()) {
                                waterConsumptionWorkId = startConsumption()
                            } else {
                                requestNotificationsPermission()
                            }
                        }) {
                            Text(text = stringResource(R.string.button_label_start))
                        }
                        TextButton(onClick = {
                            WaterConsumptionWorker.increaseBalance(amountInMilliliters = 250f)
                        }) {
                            Text(text = stringResource(R.string.button_label_drank_glass))
                        }
                        TextButton(onClick = {
                            waterConsumptionWorkId?.let(workManager::cancelWorkById)
                        }) {
                            Text(text = stringResource(R.string.button_label_stop))
                        }
                        Text(text = stringResource(R.string.label_water_balance, waterBalance))
                    }
                }

                if (showNotificationPermissionRationale) {
                    PermissionRationaleDialog(
                        onConfirmClick = {
                            requestNotificationsPermission()
                        },
                        onDismissRequest = {
                            showNotificationPermissionRationale = false
                        }
                    )
                }
            }
        }
    }

    private fun startConsumption(): UUID {
        val workRequest = OneTimeWorkRequestBuilder<WaterConsumptionWorker>()
            .setExpedited(RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
        workManager.enqueue(workRequest)
        return workRequest.id
    }

    private fun isNotificationPermitted(): Boolean =
        if (Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

    private fun shouldShowNotificationPermissionRationale() =
        shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)
}

@Composable
@RequiresApi(VERSION_CODES.TIRAMISU)
fun PermissionRationaleDialog(
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.permission_rationale_dialog_title))
        },
        text = {
            Text(text = stringResource(R.string.permission_rationale_dialog_text))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClick()
                }
            ) {
                Text(stringResource(R.string.permission_rationale_dialog_confirm_button_label))
            }
        },
        onDismissRequest = {
            onDismissRequest()
        }
    )
}
