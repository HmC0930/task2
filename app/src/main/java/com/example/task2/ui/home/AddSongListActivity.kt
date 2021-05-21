package com.example.task2.ui.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task2.MainActivity
import com.example.task2.MusicApplication.Companion.context
import com.example.task2.R
import com.example.task2.data.Repository
import com.example.task2.model.SongList
import kotlinx.android.synthetic.main.activity_add_song_list.*

class AddSongListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_song_list)

        btn_add.setOnClickListener {
            val newSongList =  SongList()
            newSongList.listName = edit_new_song_list.text.toString()
            Repository.songLists.add(newSongList)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn_add_n.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}