package com.example.task2.data.dao

import android.content.Context
import androidx.core.content.edit
import com.example.task2.MusicApplication
import com.example.task2.model.SongList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object SongListDao {
    fun saveSongLists(songLists: ArrayList<SongList>) {
        sharedPreferences().edit(){
            putInt("size", songLists.size)
        }
        for (i in 0 until songLists.size){
            sharedPreferences().edit(){
                putString(i.toString(),Gson().toJson(songLists[i]))
            }
        }
    }

    fun getSongLists():ArrayList<SongList> {
        val songLists = ArrayList<SongList>()
        val size = sharedPreferences().getInt("size",0)
        for (i in 0 until size){
            val songListJson = sharedPreferences().getString(i.toString(),"")
            songLists.add(Gson().fromJson(songListJson, SongList::class.java))
        }
        return songLists
    }

    private fun sharedPreferences() = MusicApplication.context.
            getSharedPreferences("music", Context.MODE_PRIVATE)
}