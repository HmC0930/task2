package com.example.task2.ui.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2.Model.Music
import com.example.task2.R
import com.example.task2.data.Repository
import kotlinx.android.synthetic.main.fragment_musiclist.*

class MusicListFragment : Fragment() {
    var position = 0
    val viewModel by lazy { ViewModelProvider(this).get(MusicListViewModel::class.java) }
    private lateinit var adapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle!=null) {
            position = bundle.getInt("position")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutManager = LinearLayoutManager(activity)
        musicListRecyclerView.layoutManager = layoutManager
        val adapter = MusicAdapter(this,Repository.collectionList[position].musics)
        musicListRecyclerView.adapter = adapter
        return inflater.inflate(R.layout.fragment_musiclist,container,false)

    }

}