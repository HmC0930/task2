package com.example.task2.notification

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.task2.MusicApplication
import com.example.task2.ui.home.AddSongListActivity

class NotificationClickReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val intent = Intent(MusicApplication.context, AddSongListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        MusicApplication.context.startActivity(intent)
    }
}