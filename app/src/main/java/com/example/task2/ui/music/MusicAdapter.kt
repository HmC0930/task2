package com.example.task2.ui.music

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.MainActivity.Companion.binder
import com.example.task2.model.Music
import com.example.task2.R

class MusicAdapter (val musicList: List<Music>):
    RecyclerView.Adapter<MusicAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val musicTitle: TextView = view.findViewById(R.id.musicArtistTextView)
        val musicArtist: TextView = view.findViewById(R.id.musicTitleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_item,parent,
            false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition

            //点击，修改播放列表，播放当前歌曲
            binder.addPlayList(musicList)
            binder.setCurrentMusic(position)
            binder.play()

        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val music = musicList[position]
        holder.musicArtist.text = music.artist
        holder.musicTitle.text = music.artist
    }

    override fun getItemCount() = musicList.size
}