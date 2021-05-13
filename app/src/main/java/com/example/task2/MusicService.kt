package com.example.task2

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import com.example.task2.model.Music
import com.example.task2.MusicApplication.Companion.context
import java.io.IOException

class MusicService : Service() {
    var isNeedReload = true
    val player = MediaPlayer()
    var playingMusicList = ArrayList<Music>()
    val binder = MusicServiceBinder()
    val audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager
    var playMode = 0
    lateinit var currentMusic : Music


    override fun onCreate() {
        super.onCreate()
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

    inner class MusicServiceBinder : Binder() {

        fun getPlayDuration() : Int {
            return player.duration
        }

        fun getPlayProgress() : Int {
            return player.currentPosition
        }

        fun addPlayList(item: Music) {
            if(!playingMusicList.contains(item)){
                playingMusicList.add(0,item)
            }
        }

        fun addPlayList(items: List<Music>) {
            playingMusicList.clear()
            playingMusicList.addAll(items)
        }

        fun playNext() {
            if (playMode == MusicApplication.TYPE_RANDOM){
                val i = (0 + Math.random() * (playingMusicList.size + 1)) as Int
                currentMusic = playingMusicList[i]
            }else{
                val currentIndex = playingMusicList.indexOf(currentMusic)
                if (currentIndex < playingMusicList.size - 1){
                    currentMusic = playingMusicList[currentIndex + 1]
                }else{
                    currentMusic = playingMusicList[0]
                }
            }
            isNeedReload = true
            play()
        }

        fun playPrevious() {
            var currentIndex = playingMusicList.indexOf(currentMusic)
            if (currentIndex - 1 >= 0){
                currentMusic = playingMusicList.get(currentIndex - 1)
                isNeedReload = true
                play()
            }

        }

        fun pause() {
            if(player.isPlaying){
                player.pause()
            }
        }


        fun play() {
            //获取音频焦点
            val attributesBuilder = AudioAttributes.Builder()
            attributesBuilder.setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            val requestBuilder = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)

            requestBuilder.setAudioAttributes(attributesBuilder.build())
                .setAcceptsDelayedFocusGain(false)
            val audioFocusVoice = requestBuilder.build()
            val voiceFocus = audioManager.requestAudioFocus(audioFocusVoice)

            if (currentMusic == null && !playingMusicList.isEmpty()){
                currentMusic = playingMusicList[0]
                isNeedReload = true
            }
            playMusicItem(currentMusic, isNeedReload)
        }

        private fun prepareToPlay(item: Music){
            try {
                player.reset()
                player.setDataSource(context,Uri.parse(item.path))
                player.prepare()

            }catch (e:IOException){
                e.printStackTrace()
            }
        }

        private fun playMusicItem(currentMusic: Music, isNeedReload: Boolean){
            if (currentMusic == null) {
                return
            }
            if (isNeedReload) {
                prepareToPlay(currentMusic)
            }
            player.start()
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
    }

}