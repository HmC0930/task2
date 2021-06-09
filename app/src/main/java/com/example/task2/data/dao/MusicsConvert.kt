package com.example.task2.data.dao

import androidx.room.TypeConverter
import com.example.task2.model.Music
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MusicsConvert {
    @TypeConverter
    fun stringToObject(value: String): List<Music> {
        val listType = object : TypeToken<List<Music>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<Music>):String {
        val gson = Gson()
        return gson.toJson(list)
    }

}