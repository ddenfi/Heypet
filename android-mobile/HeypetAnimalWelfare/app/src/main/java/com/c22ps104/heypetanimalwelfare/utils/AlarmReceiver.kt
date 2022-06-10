package com.c22ps104.heypetanimalwelfare.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.c22ps104.heypetanimalwelfare.R
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getIntExtra(EXTRA_TYPE,0)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val id = intent.getIntExtra(EXTRA_ID,0)

        val title = if (type == 1) TYPE_ONE_TIME else TYPE_REPEATING

        // Show Toast when alarm is ringing
        showToast(context, title, message)

        Log.d("Reminder", message.toString())

        val intentBroadcast = Intent("REMINDER_BROADCAST")
        intentBroadcast.putExtra(EXTRA_ID, id)
        intentBroadcast.putExtra(EXTRA_TYPE, type)
        context.sendBroadcast(intentBroadcast)
        // notifyToActivity

        // Show alarm notification
        if (message != null) showAlarmNotification(context, title, message, id)
    }

    // Method for notification
    private fun showAlarmNotification(context: Context, title: String, message: String, notificationId: Int) {
        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_access_time_24)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        // Android Oreo and above requires adding notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create or update
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notificationId, notification)
    }

    // Method for one-time alarm
    fun setOneTimeAlarm(context: Context, type: Int, date: Calendar, message: String, id:Int) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)
        intent.putExtra(EXTRA_ID, id)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, date.timeInMillis, pendingIntent)
    }

    // Method for repeating alarm
    fun setRepeatingAlarm(context: Context, type: Int, date: Calendar, message: String, id:Int) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)
        intent.putExtra(EXTRA_ID,id)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, date.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    // Use this method to check if the alarm is already registered in the alarm manager or not
    fun isAlarmSet(context: Context, type: String): Boolean {
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) ID_ONETIME else ID_REPEATING

        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null
    }

    fun cancelAlarm(context: Context, idReminder:Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(context, idReminder, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    // Method for Toast
    private fun showToast(context: Context, title: String, message: String?) {
        if(message != null) Toast.makeText(context, "$title: $message", Toast.LENGTH_LONG).show()
        else Toast.makeText(context, title, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val TYPE_ONE_TIME = "Heypet One Time Reminder"
        const val TYPE_REPEATING = "Heypet Everyday Reminder"

        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
        const val EXTRA_ID = "id"

        // IDs for two kinds of alarms, onetime and repeating
        private const val ID_ONETIME = 100
        private const val ID_REPEATING = 101

    }

}