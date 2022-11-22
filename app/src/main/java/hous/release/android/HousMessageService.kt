package hous.release.android

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class HousMessageService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Timber.d("remote Message $remoteMessage")
        Timber.d("remote Message Data : ${remoteMessage.data}")
        Timber.d("remote Message notification : ${remoteMessage.notification}")
        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data[TITLE] ?: ""
            val body = remoteMessage.data[BODY] ?: ""
            sendNotification(title, body)
        }
    }

    private fun sendNotification(title: String, body: String) {
        createNotificationChannel()
        val notificationBuilder = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.mipmap.ic_app_logo)
            .setContentText(body)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "hous_channel"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_NAME = "hous_channel_name"
        const val TITLE = "title"
        const val BODY = "body"
    }
}
