package com.example.task2.ui.music

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.MainActivity
import com.example.task2.MainActivity.Companion.binder
import com.example.task2.MusicApplication
import com.example.task2.MusicApplication.Companion.context
import com.example.task2.R
import com.example.task2.model.Music
import kotlinx.android.synthetic.main.music_item.view.*


class MusicAdapter(val musicList: List<Music>) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val musicArtist: TextView = view.findViewById(R.id.musicArtistTextView)
        val musicTitle: TextView = view.findViewById(R.id.musicTitleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.music_item, parent,
            false
        )
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            binder.addPlayList(musicList)
            binder.setCurrentMusic(position)
            binder.play()

        }

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("从歌单中删除")
                setMessage("确定删除歌曲？")
                setCancelable(false)
                setPositiveButton("删除"){dialog, which ->
//                    DialogInterface.OnClickListener { dialog, which ->
//                        musicList.removeAt(holder.adapterPosition)
//                    }
                }
                setNegativeButton("取消"){dialog, which ->
                }
            }
            true}

        holder.itemView.imageButton.setOnClickListener {
            val music = musicList[holder.adapterPosition]
            val intent = Intent(MusicApplication.context, AddToSongListActivity::class.java)
                .apply {
                    putExtra("music", music)
                }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            MusicApplication.context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music = musicList[position]
        holder.musicArtist.text = music.artist
        holder.musicTitle.text = music.title
    }

    override fun getItemCount() = musicList.size
}