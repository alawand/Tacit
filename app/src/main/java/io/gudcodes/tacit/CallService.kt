package io.gudcodes.tacit

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.telecom.Call
import android.util.Log


@TargetApi(Build.VERSION_CODES.O)
class CallService : android.telecom.InCallService() {

    companion object {
        private const val LOG_TAG = "CallService"
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Tacit", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


//    override fun onCallAdded(call: Call) {
//        super.onCallAdded(call)
//        Log.i(LOG_TAG, "onCallAdded: $call")
//        call.registerCallback(callCallback)
//        //startActivity(Intent(this, MainActivity::class.java))
//        //MainActivity.updateCall(call)
//
//        var mBuilder = NotificationCompat.Builder(this, "DBM")
//            .setSmallIcon(R.drawable.ic_menu_send)
//            .setContentTitle("CALL ADDED YO")
//            .setContentText("IS CALLING YOU")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//        with(NotificationManagerCompat.from(this)) {
//            createNotificationChannel()
//            // notificationId is a unique int for each notification that you must define
//            notify(1, mBuilder.build())
//        }
//
//        Log.i(LOG_TAG, call.toString())
//        Log.i(LOG_TAG, call.details.handle.toString())
//
//        if (call.details.handle.compareTo(Uri.parse("tel:6505551213")) == 0) {
//            //call.reject(false, null)
//            call.disconnect()
//        } else {
//            //call.handoverTo()
////            val intent= Intent(Intent.ACTION_DIAL, tele)
////
////            val activities: List<ResolveInfo> = packageManager.queryIntentActivities(
////                intent,
////                PackageManager.MATCH_DEFAULT_ONLY
////            )
////
////            activities[0].
//        }
//    }
//
//    override fun onCallRemoved(call: Call) {
//        super.onCallRemoved(call)
//        Log.i(LOG_TAG, "onCallRemoved: $call")
//        call.unregisterCallback(callCallback)
//        //MainActivity.updateCall(null)
//    }
//
//    private val callCallback = object : Call.Callback() {
//        override fun onStateChanged(call: Call, state: Int) {
//            Log.i(LOG_TAG, "Call.Callback onStateChanged: $call, state: $state")
//            //MainActivity.updateCall(call)
//        }
//    }

}