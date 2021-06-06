package com.example.task2


import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.task2.MusicApplication.Companion.context
import com.example.task2.model.Music
import com.example.task2.notification.NotificationClickReceiver
import com.example.task2.ui.player.PlayerFragment
import java.io.IOException

class MusicService : Service() {
    var isCurrentMusicExists = false
    var isNeedReload = true
    val player = MediaPlayer()
    var playingMusicList = ArrayList<Music>()
    private val binder = MusicServiceBinder()
    private val audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager
    var playMode = MusicApplication.TYPE_ORDER
    var currentMusic : Music? = null
    private val musicReceiver = MusicReceiver()
    private val remoteViews = RemoteViews(context.packageName, R.layout.notification)
    private lateinit var notificationManager: NotificationManager
    var notification: Notification? = null

    override fun onCreate() {
        super.onCreate()
        player.setOnCompletionListener{
            playNextInner()
        }

        initRemoteViews()
        registerMusicReceiver()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, NotificationClickReceiver::class.java)
        val pendingIntent = PendingIntent
            .getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val channelID = "channelID"
        val channel = NotificationChannel(
            channelID,
            "channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        notification = NotificationCompat.Builder(this, channelID)
            .setContentIntent(pendingIntent)
            .setCustomContentView(remoteViews)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_play)
            .setOngoing(true)
            .setAutoCancel(false)
            .setOnlyAlertOnce(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
        notificationManager.notify(1, notification)


    }

    override fun onDestroy() {
        super.onDestroy()
        if (player.isPlaying){
            player.stop()
        }
        player.release()
        playingMusicList.clear()
        if (musicReceiver != null){
            unregisterReceiver(musicReceiver)
        }
    }

    override fun onBind(intent: Intent): MusicServiceBinder {
        return binder
    }

    private fun initRemoteViews() {
        val intentPrev = Intent("prev")
        val prevPendingIntent = PendingIntent
            .getBroadcast(this, 0, intentPrev, 0)
        remoteViews.setOnClickPendingIntent(R.id.btn_notification_previous, prevPendingIntent)

        val intentPlay = Intent("play")
        val playPendingIntent = PendingIntent
            .getBroadcast(this, 0, intentPlay, 0)
        remoteViews.setOnClickPendingIntent(R.id.btn_notification_play, playPendingIntent)

        val intentNext = Intent("next")
        val nextPendingIntent = PendingIntent
            .getBroadcast(this, 0, intentNext, 0)
        remoteViews.setOnClickPendingIntent(R.id.btn_notification_next, nextPendingIntent)

        val intentClose = Intent("close")
        val closePendingIntent = PendingIntent
            .getBroadcast(this, 0, intentClose, 0)
        remoteViews.setOnClickPendingIntent(R.id.btn_notification_close, closePendingIntent)
    }

    inner class MusicReceiver : BroadcastReceiver(){
        private val tag = "MusicReceiver"
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null ) {
                intent.action?.let { UIControl(it, tag) }
            }
        }

    }

    fun UIControl(state: String, tag: String){
        val msg = PlayerFragment.handler.obtainMessage()
        when(state) {
            "prev" -> {
                binder.playPrevious()
                msg.what = 2
            }
            "play" -> {
                if (player.isPlaying){
                    binder.pause()
                    msg.what = 1
                }else{
                    binder.play()
                    msg.what = 2
                }
            }
            "next" -> {
                binder.playNext()
                msg.what = 2
            }
            "close" -> {
                player.pause()
                notificationManager.cancel(1)
            }
        }
        PlayerFragment.handler.sendMessage(msg)
    }

    private fun registerMusicReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("play")
        intentFilter.addAction("prev")
        intentFilter.addAction("next")
        intentFilter.addAction("close")
        registerReceiver(musicReceiver, intentFilter)
    }


    fun updateNotificationShow() {
        if (player.isPlaying){
            remoteViews.setImageViewResource(R.id.btn_notification_play,R.drawable.pause_black)
        }else{
            remoteViews.setImageViewResource(R.id.btn_notification_play,R.drawable.play_black)
        }
        remoteViews.setTextViewText(R.id.notification_song_name, binder.getCurrentMusicTitle())
        remoteViews.setTextViewText(R.id.notification_singer, binder.getCurrentMusicArtist())
        notificationManager?.notify(1, notification)
    }

    fun playNextInner(){
        if (currentMusic == null) {
            return
        }
        currentMusic = if (playMode == MusicApplication.TYPE_RANDOM){
            val i = (0 until playingMusicList.size).random()
            playingMusicList[i]
        }else{
            val currentIndex = playingMusicList.indexOf(currentMusic)
            if (currentIndex < playingMusicList.size - 1){
                playingMusicList[currentIndex + 1]
            }else{
                playingMusicList[0]
            }
        }
        isNeedReload = true
        playInner()
    }

    fun playInner(){
        //获取音频焦点
        if (currentMusic == null) {
            return
        }
        if (currentMusic == null && playingMusicList.size > 0 ){
            currentMusic = playingMusicList[0]
            isNeedReload = true
        }
        val attributesBuilder = AudioAttributes.Builder()
        attributesBuilder.setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        val requestBuilder = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)

        requestBuilder.setAudioAttributes(attributesBuilder.build())
                .setAcceptsDelayedFocusGain(false)
        val audioFocusVoice = requestBuilder.build()
        val voiceFocus = audioManager.requestAudioFocus(audioFocusVoice)

        if (currentMusic == null && playingMusicList.isNotEmpty()){
            currentMusic = playingMusicList[0]
            isNeedReload = true
        }
        currentMusic?.let { playMusicItemInner(it, isNeedReload) }
    }

    fun playMusicItemInner(currentMusic: Music, isNeedReload: Boolean){
        if (currentMusic == null) {
            return
        }
        if (isNeedReload) {
            prepareToPlayInner(currentMusic)
        }
        player.start()
        updateNotificationShow()
    }

    fun prepareToPlayInner(currentMusic: Music){
        try {
            player.reset()
            player.setDataSource(context, Uri.parse(currentMusic.path))
            player.prepare()

        }catch (e: IOException){
            e.printStackTrace()
        }
    }


    inner class MusicServiceBinder : Binder() {
        fun getPlayer() : MediaPlayer{
            return  player
        }

        fun isCurrentMusicNull() : Boolean{
            return currentMusic == null
        }

        fun getCurrentMusic() : Music? {
            return  currentMusic
        }

        fun getCurrentMusicTitle() : String {
            return currentMusic?.title.toString()
        }

        fun getCurrentMusicArtist() : String {
            return currentMusic?.artist.toString()
        }

        fun getPlayDuration() : Int {
            return player.duration
        }

        fun getPlayProgress() : Int {
            return player.currentPosition
        }

        fun addToPlayList(item: Music) {
            if(!playingMusicList.contains(item)){
                playingMusicList.add(0, item)
            }
        }

        fun addPlayList(items: List<Music>) {
            playingMusicList.clear()
            playingMusicList.addAll(items)
        }

        fun playNext() {
            playNextInner()
            updateNotificationShow()
        }

        fun playPrevious() {
            if (currentMusic == null) {
                return
            }
            var currentIndex = playingMusicList.indexOf(currentMusic)
            if (currentIndex - 1 >= 0){
                currentMusic = playingMusicList.get(currentIndex - 1)
                isNeedReload = true
                play()
            }
            updateNotificationShow()
        }

        fun pause() {
            if (currentMusic == null) {
                return
            }
            if(player.isPlaying){
                player.pause()
                isNeedReload = false
            }
            updateNotificationShow()
        }


        fun play() {
           playInner()
            updateNotificationShow()
        }

        private fun prepareToPlay(item: Music){
            prepareToPlayInner(item)
        }

        private fun playMusicItem(currentMusic: Music, isNeedReload: Boolean){
            playMusicItemInner(currentMusic, isNeedReload)
        }

        fun setPlayMode(mode: Int) {
            playMode = mode
        }

        fun getPlayMode(): Int {
            return playMode
        }

        fun seekTo(pos: Int) {
            player.seekTo(pos)
        }

        fun setCurrentMusic(index: Int) {
            currentMusic = playingMusicList[index]
        }

        fun isPlaying(): Boolean {
            return player.isPlaying
        }
    }

}