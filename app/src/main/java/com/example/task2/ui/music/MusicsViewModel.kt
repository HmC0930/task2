package com.example.task2.ui.music

import androidx.lifecycle.ViewModel
import com.example.task2.data.Repository
import com.example.task2.model.Music
import com.example.task2.model.SongList

class MusicsViewModel : ViewModel() {
    val currentSongListPosition = 0



    fun getSongLists() : List<SongList> {
        return Repository.songLists
    }
}