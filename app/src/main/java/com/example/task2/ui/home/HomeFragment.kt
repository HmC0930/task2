package com.example.task2.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.MusicApplication
import com.example.task2.R
import com.example.task2.model.SongList
import com.example.task2.ui.music.SongListAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val rootView = inflater.inflate(R.layout.fragment_home,container,false)
        val musicListsRecyclerView = rootView.findViewById(R.id.musicListsRecyclerView) as RecyclerView
        val layoutManager = GridLayoutManager(context,
                2)
        musicListsRecyclerView.layoutManager = layoutManager
        val adapter = SongListAdapter(this, homeViewModel.getSongLists() as ArrayList<SongList>)
        musicListsRecyclerView.adapter = adapter

        val newsonglistbutton = rootView.findViewById<Button>(R.id.newsonglistbutton)
        newsonglistbutton.setOnClickListener {
            val intent = Intent(MusicApplication.context, AddSongListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            MusicApplication.context.startActivity(intent)
        }

        return rootView
    }

}