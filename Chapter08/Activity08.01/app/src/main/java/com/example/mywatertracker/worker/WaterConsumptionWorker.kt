package com.example.mywatertracker.worker

import android.annotation.SuppressLint
import android.app.Notification.FOREGROUND_SERVICE_IMMEDIATE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.mywatertracker.MainActivity
import com.example.mywatertracker.R
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicInteger

class WaterConsumptionWorker(
    context: Context,
    parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {
    private val channelId by lazy {
        createNotificationChannel()
    }

    override suspend fun doWork(): Result {
        setForeground(getForegroundInfo())
        while (true) {
            waterBalance.getAndAdd(-144)
            setProgress(workDataOf(DATA_KEY_WATER_BALANCE to waterBalanceInMilliliters()))
            updateNotification()
            delay(5000)
        }
        return Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notification = getNotification()

        return if (Build.VERSION.SDK_INT >= VERSION_CODES.Q) {
            ForegroundInfo(
                NOTIFICATION_ID, notification,
                FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            ForegroundInfo(NOTIFICATION_ID, notification)
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateNotification() {
        val notification = getNotification()
        NotificationManagerCompat
            .from(applicationContext)
            .notify(NOTIFICATION_ID, notification)
    }

    private fun getPendingIntent(): PendingIntent {
        val flag = if (Build.VERSION.SDK_INT >= VERSION_CODES.S) {
            FLAG_IMMUTABLE
        } else {
            0
        }
        return PendingIntent.getActivity(
            applicationContext, 0, Intent(applicationContext, MainActivity::class.java), flag
        )
    }

    private fun getNotification() = getNotificationBuilder()
        .setContentText(String.format("Water level is %.3f", waterBalanceInMilliliters()))
        .build()

    private fun getNotificationBuilder(): NotificationCompat.Builder {
        val pendingIntent = getPendingIntent()
        return NotificationCompat.Builder(
            applicationContext, channelId
        ).setContentTitle("Water balance")
            .setSmallIcon(
                R.drawable.ic_launcher_foreground
            ).setContentIntent(pendingIntent)
            .setTicker(
                "Tracking water balance"
            ).setOngoing(true)
            .setForegroundServiceBehavior(
                FOREGROUND_SERVICE_IMMEDIATE
            ).setOnlyAlertOnce(true)
    }

    private fun createNotificationChannel(): String = if (
        Build.VERSION.SDK_INT >= VERSION_CODES.O
    ) {
        val newChannelId = "WaterTracking"
        val channelName = applicationContext.getString(R.string.channel_name_water_tracking)
        val channel = NotificationChannel(newChannelId, channelName, IMPORTANCE_HIGH)
        val service = requireNotNull(
            ContextCompat.getSystemService(applicationContext, NotificationManager::class.java)
        )
        service.createNotificationChannel(channel)
        newChannelId
    } else {
        ""
    }

    companion object {
        private const val NOTIFICATION_ID = 0x3A7E12
        private val waterBalance: AtomicInteger = AtomicInteger(0)

        const val DATA_KEY_WATER_BALANCE = "WaterLevel"

        private fun waterBalanceInMilliliters() = waterBalance.toInt() / 1000f

        fun increaseBalance(amountInMilliliters: Float) {
            waterBalance.addAndGet((amountInMilliliters * 1000f).toInt())
        }
    }
}
