package com.example.task2.ui.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.model.SongList
import com.example.task2.R


class SongListAdapter(val fragment: Fragment, val songListList: List<SongList>) :
    RecyclerView.Adapter<SongListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.collectionNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.collection_item, parent,
            false
        )
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position = holder.adapterPosition


            val fm = fragment.childFragmentManager
            val ft = fm.beginTransaction()
            val musicListFragment = MusicsFragment()
            val bundle = Bundle()
            bundle.putInt("position",position)
            musicListFragment.arguments = bundle
            ft.replace(R.id.fragment_container_view_tag,musicListFragment)
            ft.addToBackStack(null)
            ft.commit()

        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collection = songListList[position]
        holder.name.text = collection.listName
    }

    override fun getItemCount() = songListList.size
}