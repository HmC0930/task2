package com.example.task2.ui.player

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.task2.MainActivity.Companion.binder
import com.example.task2.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_player.*
import java.util.*
import kotlin.concurrent.thread

class PlayerFragment : Fragment() {
    private lateinit var playerViewModel: PlayerViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        playerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_player,container,false)

        val currentMusicTitle = rootView.findViewById<AppCompatTextView>(R.id.current_music_title)
        currentMusicTitle.text = binder.getCurrentMusicTitle()

        val currentMusicArtist = rootView.findViewById<AppCompatTextView>(R.id.current_music_artist)
        currentMusicArtist.text = binder.getCurrentMusicArtist()

        val playButton = rootView.findViewById<AppCompatImageButton>(R.id.playButton)
        playButton.setOnClickListener {
            binder.play()
        }

        val pauseButton = rootView.findViewById<AppCompatImageButton>(R.id.pauseButton)
        pauseButton.setOnClickListener {
            binder.pause()
        }

        val nextButton = rootView.findViewById<AppCompatImageButton>(R.id.nextButton)
        nextButton.setOnClickListener {
            binder.playNext()
            currentMusicArtist.text = binder.getCurrentMusicArtist()
            currentMusicTitle.text = binder.getCurrentMusicTitle()
        }

        val previousButton = rootView.findViewById<AppCompatImageButton>(R.id.previousButton)
        previousButton.setOnClickListener {
            binder.playPrevious()
            currentMusicArtist.text = binder.getCurrentMusicArtist()
            currentMusicTitle.text = binder.getCurrentMusicTitle()
            seekBar.max = binder.getPlayDuration()
        }

        val seekBar = rootView.findViewById<AppCompatSeekBar>(R.id.seekBar)
        if (binder.isCurrentMusicNull()){
            seekBar.isEnabled = false
        }
        else if (binder.isPlaying()){
            seekBar.isEnabled = true
            seekBar.max = binder.getPlayDuration()
            seekBar.progress = binder.getPlayProgress()
        }

        val task = MyTimerTask()

        Timer().schedule(task,500,500)

        binder.getPlayer().setOnCompletionListener {
            binder.playNext()
            currentMusicArtist.text = binder.getCurrentMusicArtist()
            currentMusicTitle.text = binder.getCurrentMusicTitle()
            seekBar.max = binder.getPlayDuration()
        }

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                binder.seekTo(seekBar.progress)
            }
        })

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    inner class MyTimerTask() : TimerTask() {
        override fun run() {
            if (!binder.isCurrentMusicNull() && seekBar != null){
                seekBar.progress =  binder.getPlayProgress()
            }
        }
    }
}