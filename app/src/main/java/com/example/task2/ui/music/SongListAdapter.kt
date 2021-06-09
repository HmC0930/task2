package com.example.task2.ui.music

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.MusicApplication.Companion.context
import com.example.task2.R
import com.example.task2.model.Music
import com.example.task2.model.SongList


class SongListAdapter(val fragment: Fragment, val songListList: List<SongList>) :
    RecyclerView.Adapter<SongListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.collectionNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.songlist_item, parent,
            false
        )
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position = holder.adapterPosition
            val intent = Intent(context, MusicsActivity::class.java).apply {
                putExtra("currentSongListPosition",position)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collection = songListList[position]
        holder.name.text = collection.listName
    }

    override fun getItemCount() = songListList.size

}