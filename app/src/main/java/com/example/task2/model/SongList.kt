package com.example.task2.model

class SongList {
    var listName: String = ""
    var musics = ArrayList<Music>()
    fun add(music: Music) {
        if (musics != null && !musics.contains(music)) {
            musics.add(music)
        }
    }

    fun delete(music: Music) {
        if (musics != null && musics.contains(music)) {
            musics.remove(music)
        }
    }

}