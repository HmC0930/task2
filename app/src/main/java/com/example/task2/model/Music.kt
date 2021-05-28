package com.example.task2.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@SuppressLint("ParcelCreator")
@Parcelize
data class Music(
    val title: String, val artist: String,
    val size: Long, val duration: Long, val path: String
) : Parcelable