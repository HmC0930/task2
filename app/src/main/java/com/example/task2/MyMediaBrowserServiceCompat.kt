package com.example.task2

import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat



class MyMediaBrowserServiceCompat : MediaBrowserServiceCompat(){
    var mMediaPlayer: MediaPlayer = MediaPlayer()
    var mRepeatMode: Int = PlaybackStateCompat.REPEAT_MODE_NONE

    //播放状态，通过 MediaSession 回传给 UI 端。
    var mState = PlaybackStateCompat.Builder().build()

    //Service 需要保存播放列表，并处理循环模式
    var mPlayList = arrayListOf<MediaSessionCompat.QueueItem>()
    var mMusicIndex = -1
    var mCurrentMedia: MediaSessionCompat.QueueItem? = null
    lateinit var mSession: MediaSessionCompat
    var mSessionCallback = object : MediaSessionCompat.Callback() {

    }
    var mCompletionListener = MediaPlayer.OnCompletionListener {  }

    var mPreparedListener = MediaPlayer.OnPreparedListener {  }


    override fun onCreate() {
        super.onCreate()
        mSession = MediaSessionCompat(applicationContext, "MusicService")
        mSession.setCallback(mSessionCallback)
        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS)
        sessionToken = mSession.sessionToken
        mMediaPlayer.setOnCompletionListener(mCompletionListener)
        mMediaPlayer.setOnPreparedListener(mPreparedListener)
        mMediaPlayer.setOnErrorListener { mp, what, extra -> true }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        MainActivity.mediaBrowserServiceCompat = this
        return super.onBind(intent)
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        TODO("Not yet implemented")
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        TODO("Not yet implemented")
    }

}