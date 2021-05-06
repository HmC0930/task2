package com.example.task2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task2.data.Repository
import com.example.task2.ui.SongList.SongListAdapter
import kotlinx.android.synthetic.main.fragment_musiclist.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutManager = GridLayoutManager(context,2)
        musicListRecyclerView.layoutManager = layoutManager
        val adapter = SongListAdapter(this,Repository.collectionList)
        musicListRecyclerView.adapter = adapter

        return super.onCreateView(inflater, container, savedInstanceState)
    }

}