package com.example.task2.data

import android.os.Build
import android.provider.MediaStore
import android.provider.Telephony
import androidx.annotation.RequiresApi
import com.example.task2.model.SongList
import com.example.task2.model.Music
import com.example.task2.MusicApplication

object Repository {
    val songLists = ArrayList<SongList>()
    val localMusics = getLocalMusic()

    private fun getLocalMusic(): List<Music> {
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
        songList.musics.addAll(list)
        songLists.add(songList)
        cursor?.close()
        return list
    }


}