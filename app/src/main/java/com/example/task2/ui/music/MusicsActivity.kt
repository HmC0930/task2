package com.example.task2.ui.music

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task2.MusicApplication.Companion.context
import com.example.task2.R
import com.example.task2.data.Repository
import kotlinx.android.synthetic.main.activity_musics.*

class MusicsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musics)
        val currentSongListPosition = intent.getIntExtra("currentSongListPosition",0)
        val layoutManager = GridLayoutManager(context,
            1)
        musicsRecyclerView.layoutManager = layoutManager
        val adapter = MusicAdapter(Repository.songLists[currentSongListPosition].musics)
        musicsRecyclerView.adapter = adapter
    }
}