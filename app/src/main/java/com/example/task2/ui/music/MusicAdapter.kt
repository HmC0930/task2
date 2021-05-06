package com.example.task2.ui.music

import android.media.session.MediaSession
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.task2.MainActivity
import com.example.task2.Model.Music
import com.example.task2.R
import kotlinx.android.synthetic.main.music_item.*

class MusicAdapter (val fragment: Fragment,val musicList: List<Music>):
    RecyclerView.Adapter<MusicAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val musicTitle: TextView = view.findViewById(R.id.musicArtistTextView)
        val musicArtist: TextView = view.findViewById(R.id.musicTitleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_item,parent,
            false)
        val holder = ViewHolder(view)
        val queueItemList = ArrayList<MediaSession.QueueItem>()
        musicList.forEach{

        }
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            MainActivity.mediaBrowserServiceCompat.mMediaPlayer.setDataSource(musicList[position].path)
            MainActivity.mediaBrowserServiceCompat.mPlayList.addAll(queueItemList)
            MainActivity.mediaBrowserServiceCompat.mMediaPlayer.prepare()
            MainActivity.mediaBrowserServiceCompat.mMediaPlayer.start()

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