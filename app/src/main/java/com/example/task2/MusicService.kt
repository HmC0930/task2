package com.example.task2

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.util.Log
import com.example.task2.model.Music
import com.example.task2.MusicApplication.Companion.context
import java.io.IOException

class MusicService : Service() {
    var isCurrentMusicExists = false
    var isNeedReload = true
    val player = MediaPlayer()
    var playingMusicList = ArrayList<Music>()
    val binder = MusicServiceBinder()
    val audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager
    var playMode = 0
    var currentMusic : Music? = null

    override fun onCreate() {
        super.onCreate()
        player.setOnCompletionListener{
            playNextInner()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (player.isPlaying){
            player.stop()
        }
        player.release()
        playingMusicList.clear()

    }

    override fun onBind(intent: Intent): MusicServiceBinder {
        return binder
    }


    fun playNextInner(){
        if (currentMusic == null) {
            return
        }
        currentMusic = if (playMode == MusicApplication.TYPE_RANDOM){
            val i = (0 + Math.random() * (playingMusicList.size + 1)) as Int
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
    }

    fun prepareToPlayInner(currentMusic: Music){
        try {
            player.reset()
            player.setDataSource(context,Uri.parse(currentMusic.path))
            player.prepare()

        }catch (e:IOException){
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
                playingMusicList.add(0,item)
            }
        }

        fun addPlayList(items: List<Music>) {
            playingMusicList.clear()
            playingMusicList.addAll(items)
        }

        fun playNext() {
            playNextInner()
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

        }

        fun pause() {
            if (currentMusic == null) {
                return
            }
            if(player.isPlaying){
                player.pause()
                isNeedReload = false
            }
        }


        fun play() {
           playInner()
        }

        private fun prepareToPlay(item: Music){
            prepareToPlayInner(item)
        }

        private fun playMusicItem(currentMusic: Music, isNeedReload: Boolean){
            playMusicItemInner(currentMusic,isNeedReload)
        }

        fun setPlayMode(mode : Int) {
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