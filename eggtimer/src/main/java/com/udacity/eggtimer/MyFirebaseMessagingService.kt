package com.udacity.eggtimer

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.udacity.eggtimer.util.getNotificationManager
import com.udacity.eggtimer.util.sendNotification
import timber.log.Timber

/**
 * If app is background -- fcm send a notification
 *  1. Data message --> Show default notification and onMessageReceived is not  triggered
 *  2. Notification message --> Show default notification and onMessageReceived is not triggered
 *
 * ****
 *
 * If app is foreground
 *  Not showing default notification
 *  onMessageReceived is triggered  both data and notification message
 *    1. Data message --> for data, (message.data != null)
 *    2. Notification message --> (message.notification != null)
 *                            --> for notification (You can show your custom notification with this object)
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Timber.d("Refreshed token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Timber.d("Message data payload: ${message.data}")

        message.notification?.let {
            sendNotification(it.body)
        }


    }

    private fun sendNotification(messageBody:String?) {
        getNotificationManager(this).sendNotification(
            this,
            messageBody ?: "from MyFirebaseMessagingService",
        )
    }
}