package com.example.task2.data.Network

import com.example.task2.Model.Music
import com.example.task2.Model.MusicResponse
import com.example.task2.MusicApplication
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicService {
    @GET("V2/music?" +
            "token = ${MusicApplication.TOKEN}&lang=zh_CN")
    fun searchMusics(@Query("query") query:String):
            Call<MusicResponse>
}