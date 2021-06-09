package com.example.task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.task2.data.dao.MusicsConvert
import java.io.Serializable

@Entity

data class SongList (@ColumnInfo var listName: String = ""): Serializable{

    @TypeConverters(MusicsConvert::class)
    @ColumnInfo(name = "musics")
    var musics : List<Music> = ArrayList<Music>()

    @PrimaryKey(autoGenerate = true)
    var id = 0


    fun addMusic(music: Music) {
        if (musics != null && !musics.contains(music)) {
//            musics.add(music)
            val arrayList = musics as ArrayList<Music>
            arrayList.add(music)
            musics = arrayList
        }
    }

    fun removeMusic(music: Music) {
        if (musics != null && musics.contains(music)) {
//            musics.remove(music)
            val arrayList = musics as ArrayList<Music>
            arrayList.remove(music)
            musics = arrayList
        }
    }

}