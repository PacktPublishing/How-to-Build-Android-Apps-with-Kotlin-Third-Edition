package com.example.catagenttracker.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.catagenttracker.MainActivity
import com.example.catagenttracker.R
import kotlinx.coroutines.delay

class RouteTrackingWorker(
    context: Context, parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {
    private val channelId by lazy {
        createNotificationChannel()
    }

    private var secondsLeft = INITIAL_SECONDS_LEFT

    override suspend fun doWork(): Result {
        val catAgentId = inputData
            .getString(DATA_KEY_CAT_AGENT_ID) ?:
            error("Agent ID must be provided")

        setForeground(getForegroundInfo())
        trackToDestination(catAgentId)
        return Result.success()
    }

    override suspend fun getForegroundInfo() =
        createForegroundInfo(secondsLeft)

    private suspend fun trackToDestination(
        catAgentId: String
    ) {
        secondsLeft = INITIAL_SECONDS_LEFT
        for (i in INITIAL_SECONDS_LEFT downTo 0) {
            setProgress(
                workDataOf(
                    DATA_KEY_CAT_AGENT_ID to catAgentId,
                    DATA_KEY_SECONDS_LEFT to secondsLeft
                )
            )
            secondsLeft = i
            updateNotification()
            delay(1000L)
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateNotification() {
        val notification = getNotificationBuilder()
            .setContentText(
                "$secondsLeft seconds to destination"
            ).build()
        NotificationManagerCompat
            .from(applicationContext)
            .notify(NOTIFICATION_ID, notification)
    }

    private fun createForegroundInfo(
        secondsLeft: Int
    ): ForegroundInfo {
        val notification = getNotificationBuilder()
            .setContentText("$secondsLeft seconds to destination")
            .build()

        return if (Build.VERSION.SDK_INT >= VERSION_CODES.Q) {
            ForegroundInfo(
                NOTIFICATION_ID, notification,
                FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            ForegroundInfo(NOTIFICATION_ID, notification)
        }
    }

    private fun getPendingIntent(): PendingIntent {
        val flag = if (
            Build.VERSION.SDK_INT >= VERSION_CODES.S
        ) { FLAG_IMMUTABLE } else { 0 }
        return PendingIntent.getActivity(
            applicationContext, 0, Intent(
                applicationContext, MainActivity::class.java
            ), flag
        )
    }

    private fun getNotificationBuilder():
            NotificationCompat.Builder {
        val pendingIntent = getPendingIntent()
        return NotificationCompat.Builder(
            applicationContext, channelId
        ).setContentTitle("Agent approaching destination")
            .setSmallIcon(
                R.drawable.ic_launcher_foreground
            ).setContentIntent(pendingIntent)
            .setTicker(
                "Agent dispatched, tracking movement"
            ).setOngoing(true)
            .setForegroundServiceBehavior(
                FOREGROUND_SERVICE_IMMEDIATE
            ).setOnlyAlertOnce(true)
    }

    private fun createNotificationChannel(): String = if (
        Build.VERSION.SDK_INT >= VERSION_CODES.O
    ) {
        val newChannelId = "CatDispatch"
        val channelName = "Cat Dispatch Tracking"
        val channel = NotificationChannel(
            newChannelId, channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        val service = requireNotNull(
            ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            )
        )
        service.createNotificationChannel(channel)
        newChannelId
    } else { "" }

    companion object {
        private const val NOTIFICATION_ID = 0xCA7
        private const val INITIAL_SECONDS_LEFT = 10
        const val DATA_KEY_CAT_AGENT_ID = "AgentId"
        const val DATA_KEY_SECONDS_LEFT = "SecondsLeft"
    }
}
