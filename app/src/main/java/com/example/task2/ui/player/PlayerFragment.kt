package com.example.task2.ui.player

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.task2.MainActivity
import com.example.task2.MainActivity.Companion.binder
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment : Fragment() {
    private lateinit var playerViewModel: PlayerViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        playerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)

        playButton.setOnClickListener {
            binder.play()
        }

        pauseButton.setOnClickListener {
            binder.pause()
        }

        nextButton.setOnClickListener {
            binder.playNext()
        }


        previousButton.setOnClickListener {
            binder.playPrevious()
        }

        val seekBarMaxObserver = Observer<Int>{ max->
            seekBar.max = max
        }
        playerViewModel.maxLivadata.observe(viewLifecycleOwner,seekBarMaxObserver)

        val seekBarProgressObserver = Observer<Int> {progress->
            seekBar.progress = progress
        }
        playerViewModel.progressLiveData.observe(viewLifecycleOwner,seekBarProgressObserver)

        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                //拖动进度条时
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                binder.seekTo(seekBar.progress)
            }
        })

        return super.onCreateView(inflater, container, savedInstanceState)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}