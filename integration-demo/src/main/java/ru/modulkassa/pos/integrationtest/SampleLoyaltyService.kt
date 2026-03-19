package ru.modulkassa.pos.integrationtest

import android.app.ActivityOptions
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.core.OperationHandler
import ru.modulkassa.pos.integration.core.PluginService
import ru.modulkassa.pos.integration.core.handler.LoyaltyOperationHandler
import ru.modulkassa.pos.integration.entity.loyalty.LoyaltyRequest

class SampleLoyaltyService : PluginService() {

    companion object {
        private const val NOTIFICATION_CHANNEL = "sample_loyalty_service"
        private const val LOYALTY_NOTIFICATION_ID = 1234
    }

    override fun createHandlers(): List<OperationHandler> {
        return listOf(
            object : LoyaltyOperationHandler(this@SampleLoyaltyService) {
                override fun handleLoyaltyRequest(
                    loyaltyRequest: LoyaltyRequest,
                    callback: PluginServiceCallbackHolder
                ) {
                    val intent = Intent(applicationContext, SampleLoyaltyActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        putExtra(SampleLoyaltyActivity.LOYALTY_DATA, loyaltyRequest.toBundle())
                        callback.putToIntent(this)
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        showLoyaltyNotification(intent)
                    } else {
                        startActivity(intent)
                    }
                }

                @RequiresApi(Build.VERSION_CODES.O)
                private fun showLoyaltyNotification(intent: Intent) {
                    val options = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        ActivityOptions.makeBasic().apply {
                            setPendingIntentCreatorBackgroundActivityStartMode(
                                ActivityOptions.MODE_BACKGROUND_ACTIVITY_START_ALLOWED
                            )
                        }.toBundle()
                    } else {
                        null
                    }

                    val pendingIntent = PendingIntent.getActivity(
                        applicationContext,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                        options
                    )

                    val channel = NotificationChannel(
                        NOTIFICATION_CHANNEL,
                        applicationContext.getString(R.string.loyalty_button_label),
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)

                    val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
                        .setContentIntent(pendingIntent)
                        .setContentText(applicationContext.getString(R.string.notification_content_text))
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
                        .build()

                    notificationManager.notify(LOYALTY_NOTIFICATION_ID, notification)
                }
            }
        )
    }
}