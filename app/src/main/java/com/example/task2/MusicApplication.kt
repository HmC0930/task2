package com.example.task2

import android.app.Application
import android.content.Context
import com.example.task2.data.Repository

class MusicApplication : Application() {
    companion object{
        lateinit var context: Context
        const val TOKEN = " "
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}