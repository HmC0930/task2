package com.example.task2.data.dao

import android.content.Context
import androidx.core.content.edit
import androidx.room.*
import com.example.task2.MusicApplication
import com.example.task2.model.SongList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Dao
interface SongListDao {
    @Insert
    fun saveSongList(songList: SongList)

    @Insert
    fun saveSongLists(songLists: List<SongList>)

    @Update
    fun updateSongList(vararg songList: SongList)

    @Delete
    fun deleteSongList(songList: SongList)

    @Query("SELECT * FROM songList")
    fun getSongLists():List<SongList>
}