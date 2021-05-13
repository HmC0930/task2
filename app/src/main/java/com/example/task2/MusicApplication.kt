package com.example.task2

import android.app.Application
import android.content.Context
import com.example.task2.data.Repository

class MusicApplication : Application() {

    companion object{
        lateinit var context: Context
        const val TOKEN = " "
        val TYPE_ORDER = 4212//顺序播放
        val TYPE_SINGLE = 4314//单曲循环
        val TYPE_RANDOM = 4414//随机播放
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}