package com.example.task2.data

import android.provider.MediaStore
import android.provider.Telephony
import androidx.lifecycle.liveData
import com.example.task2.Model.SongList
import com.example.task2.data.Network.MusicNetwork
import com.example.task2.Model.Music
import com.example.task2.MusicApplication
import kotlinx.coroutines.Dispatchers
import kotlin.Exception

object Repository {
    val collectionList = ArrayList<SongList>()
    val localMusics = getLocalMusic()

    private fun getLocalMusic(): List<Music> {
        val list = ArrayList<Music>()
        var cursor = MusicApplication.context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null, null, null, MediaStore.Audio.Media.IS_MUSIC
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val music = Music(
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Mms.Part._DATA))
                )
                list.add(music)
            }
        }
        val collection = SongList()
        collection.listName = "本地音乐"
        collection.musics.addAll(list)
        collectionList.add(collection)
        return list
    }


    fun searchMusicsOnline (query : String) = liveData (Dispatchers.IO) {
        val result = try {
                val musicResponse = MusicNetwork.searchMusics(query)
                if (musicResponse.status == "ok"){
                    val musics = musicResponse.musics
                    Result.success(musics)
                }else{
                    Result.failure(RuntimeException("response status is ${musicResponse.status}"))
                }
            }catch (e:Exception) {
                Result.failure<List<Music>>(e)
            }
        emit(result)
    }


}