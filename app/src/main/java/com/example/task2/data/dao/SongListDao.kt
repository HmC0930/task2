package com.example.task2.data.dao

import android.content.Context
import com.example.task2.MusicApplication
import com.example.task2.model.SongList

object SongListDao {
    fun saveSongLists(songLists:ArrayList<SongList>){

    }

    private fun sharedPreferrences() = MusicApplication.context.
            getSharedPreferences("music", Context.MODE_PRIVATE)
}