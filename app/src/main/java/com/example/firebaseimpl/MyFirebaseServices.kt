package com.example.firebaseimpl

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage

class MyFirebaseServices: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("noti", "onNewToken: $token")

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if(message.notification!=null){
            message.notification!!.body?.let { message.notification!!.title?.let { it1 ->
                showNotification(
                    it1, it)
            } }
        }
    }

    private fun showNotification(title:String,body:String){
         val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
            .setContentTitle(title)
            .setContentText(body)
            .setSound(sound)
            .setAutoCancel(true)

        val notifyManager = getSystemService(Context.NOTIFICATION_SERVICE)
         as NotificationManager
        notifyManager.notify(0,notification.build())
    }
}