package com.example.task2.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task2.model.SongList

@Database(entities = [SongList::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songListDao() : SongListDao
}