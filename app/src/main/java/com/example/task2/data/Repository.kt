package com.example.task2.data

import android.os.Build
import android.provider.MediaStore
import android.provider.Telephony
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.task2.model.SongList
import com.example.task2.model.Music
import com.example.task2.MusicApplication
import com.example.task2.MusicApplication.Companion.context
import com.example.task2.data.Repository.saveSongLists
import com.example.task2.data.dao.AppDatabase
import com.example.task2.data.dao.SongListDao
import kotlin.concurrent.thread

object Repository {
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database"
    ).build()
    val dao = db.songListDao()

    var songLists = ArrayList<SongList>()

    fun getLocalMusic() {
        val list = ArrayList<Music>()
        val cursor = MusicApplication.context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null, null, null, MediaStore.Audio.Media.IS_MUSIC
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val music = Music(
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Mms.Part._DATA))
                )

                list.add(music)
            }
        }
        val songList = SongList()
        songList.listName = "本地音乐"
        songList.musics = list
        cursor?.close()
        songLists.add(songList)
        saveSongLists(songLists)
    }

    fun saveSongLists(lists: ArrayList<SongList>) {
        dao.saveSongLists(lists)
    }

    fun saveSongList(list: SongList) {
        dao.saveSongList(list)
    }

    fun getSavedSongLists() : ArrayList<SongList> {
        return dao.getSongLists() as  ArrayList<SongList>
    }

    fun updateSongList(list: SongList) {
        dao.updateSongList(list)
    }
}