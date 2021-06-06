package com.example.task2.model

import android.annotation.SuppressLint
import android.telephony.mbms.StreamingServiceInfo
import java.io.Serializable

data class SongList (var listName:String = ""): Serializable{
    var musics = ArrayList<Music>()
    fun addMusic(music: Music) {
        if (musics != null && !musics.contains(music)) {
            musics.add(music)
        }
    }

    fun removeMusic(music: Music) {
        if (musics != null && musics.contains(music)) {
            musics.remove(music)
        }
    }

}