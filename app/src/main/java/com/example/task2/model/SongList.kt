package com.example.task2.model

import android.annotation.SuppressLint
import android.telephony.mbms.StreamingServiceInfo
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
class SongList (){
    var listName: String = ""
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