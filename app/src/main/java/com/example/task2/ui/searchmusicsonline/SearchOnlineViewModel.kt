package com.example.task2.ui.searchmusicsonline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task2.model.Music

class SearchOnlineViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val onlineMusicList = ArrayList<Music>()

//    val musicLiveData = Transformations.switchMap(searchLiveData){query->
//        Repository.searchMusicsOnline(query)
//    }

    fun searchMusics(query: String) {
        searchLiveData.value = query
    }
}