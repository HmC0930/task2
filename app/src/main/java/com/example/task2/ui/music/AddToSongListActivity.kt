package com.example.task2.ui.music

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ResourceCursorAdapter
import android.widget.Toast
import com.example.task2.R
import com.example.task2.data.Repository
import com.example.task2.model.Music
import com.example.task2.model.SongList
import kotlinx.android.synthetic.main.activity_add_to_song_list.*
import kotlin.concurrent.thread

class AddToSongListActivity : AppCompatActivity() {
    var songLists = Repository.songLists
    var songListNames = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_song_list)

        val music = intent.getSerializableExtra("music")

        songLists.forEach {
            songListNames.add(it.listName)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
            songListNames)
        list_view.adapter = adapter
        list_view.setOnItemClickListener { parent, view, position, id ->
            if (Repository.songLists[position].musics.contains(music)){
                Toast.makeText(this,"歌曲已存在", Toast.LENGTH_SHORT).show()
            }
            if (music != null && !Repository.songLists[position].musics.contains(music)) {
                Repository.songLists[position].addMusic(music as Music)
                thread {
                    Repository.updateSongList(Repository.songLists[position])
                }
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show()
            }
            this.finish()
        }
        btn_select_n.setOnClickListener {
            this.finish()
        }
    }

}