package com.example.task2.model


import java.io.Serializable

data class Music(
    val title: String, val artist: String,
    val size: Long, val duration: Long, val path: String
) : Serializable