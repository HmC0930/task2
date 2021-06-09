package com.example.task2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task2.model.SongList
import com.example.task2.data.Repository

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getSongLists() : List<SongList> {
        return Repository.songLists
    }

    fun saveSongLists(songLists: ArrayList<SongList>) {
        Repository.saveSongLists(songLists)
    }

}