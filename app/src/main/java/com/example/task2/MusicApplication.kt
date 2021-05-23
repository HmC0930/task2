package com.example.task2

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toolbar
import com.example.task2.data.Repository

class MusicApplication : Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context
        const val TOKEN = " "
        const val TYPE_ORDER = 4212//顺序播放
        const val TYPE_SINGLE = 4314//单曲循环
        const val TYPE_RANDOM = 4414//随机播放
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }

    fun getContext() : Context{
        return context
    }
}