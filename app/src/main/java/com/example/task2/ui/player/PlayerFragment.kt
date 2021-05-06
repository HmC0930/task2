package com.example.task2.ui.player

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.task2.MainActivity
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment : Fragment() {
    lateinit var playerViewModel: PlayerViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        playerViewModel=ViewModelProvider(this).get(PlayerViewModel::class.java)
        seekBar.progress = MainActivity.mediaBrowserServiceCompat.mMediaPlayer.currentPosition

        playButton.setOnClickListener {
            if (!MainActivity.mediaBrowserServiceCompat.mMediaPlayer.isPlaying){
                MainActivity.mediaBrowserServiceCompat.mMediaPlayer.start()
            }
        }

        pauseButton.setOnClickListener {
            if (MainActivity.mediaBrowserServiceCompat.mMediaPlayer.isPlaying){
                MainActivity.mediaBrowserServiceCompat.mMediaPlayer.pause()
            }
        }

        nextButton.setOnClickListener {

        }



        return super.onCreateView(inflater, container, savedInstanceState)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}