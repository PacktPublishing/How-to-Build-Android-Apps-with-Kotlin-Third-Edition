package com.example.catagenttracker

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.catagenttracker.ui.theme.CatAgentTrackerTheme
import com.example.catagenttracker.worker.CatFurGroomingWorker
import com.example.catagenttracker.worker.CatLitterBoxSittingWorker
import com.example.catagenttracker.worker.CatStretchingWorker
import com.example.catagenttracker.worker.CatSuitUpWorker
import java.util.UUID

class MainActivity : ComponentActivity() {
    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val catAgentId = "CatAgent1"
        val catStretchingRequest =
            OneTimeWorkRequestBuilder<CatLitterBoxSittingWorker>()
                .setConstraints(networkConstraints)
                .setInputData(
                    workDataOf(
                        CatStretchingWorker
                            .INPUT_DATA_CAT_AGENT_ID to catAgentId
                    )
                ).build()
        val catFurGroomingRequest =
            OneTimeWorkRequestBuilder<CatFurGroomingWorker>()
                .setConstraints(networkConstraints)
                .setInputData(
                    workDataOf(
                        CatFurGroomingWorker
                            .INPUT_DATA_CAT_AGENT_ID to catAgentId
                    )
                ).build()
        val catLitterBoxSittingRequest =
            OneTimeWorkRequestBuilder<CatLitterBoxSittingWorker>()
                .setConstraints(networkConstraints)
                .setInputData(
                    workDataOf(
                        CatLitterBoxSittingWorker
                            .INPUT_DATA_CAT_AGENT_ID to catAgentId
                    )
                ).build()
        val catSuitUpRequest =
            OneTimeWorkRequestBuilder<CatSuitUpWorker>()
                .setConstraints(networkConstraints)
                .setInputData(
                    workDataOf(
                        CatSuitUpWorker
                            .INPUT_DATA_CAT_AGENT_ID to catAgentId
                    )
                ).build()

        workManager.beginWith(catStretchingRequest)
            .then(catFurGroomingRequest)
            .then(catLitterBoxSittingRequest)
            .then(catSuitUpRequest)
            .enqueue()

        enableEdgeToEdge()
        setContent {
            CatAgentTrackerTheme {
                LaunchedEffect(true) {
                    collectWorkInfo(
                        catStretchingRequest.id,
                        "Agent done stretching"
                    )
                }
                LaunchedEffect(true) {
                    collectWorkInfo(
                        catFurGroomingRequest.id,
                        "Agent done grooming its fur"
                    )
                }
                LaunchedEffect(true) {
                    collectWorkInfo(
                        catLitterBoxSittingRequest.id,
                        "Agent done sitting in litter box"
                    )
                }
                LaunchedEffect(true) {
                    collectWorkInfo(
                        catSuitUpRequest.id,
                        "Agent done suiting up. Ready to go!"
                    )
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private suspend fun collectWorkInfo(
        requestId: UUID,
        message: String
    ) {
        workManager.getWorkInfoByIdFlow(requestId)
            .collect { info ->
                if (info.state.isFinished) {
                    showResult(message)
                }
            }
    }

    private fun showResult(message: String) {
        Toast.makeText(this, message, LENGTH_SHORT).show()
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
