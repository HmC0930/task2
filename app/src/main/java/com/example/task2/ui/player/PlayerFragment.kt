package com.example.task2.ui.player

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.task2.MainActivity.Companion.binder
import com.example.task2.MusicApplication
import com.example.task2.R
import com.example.task2.ui.music.AddToSongListActivity
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_player.*
import java.text.SimpleDateFormat
import java.util.*

class PlayerFragment : Fragment() {
    companion object{
        lateinit var handler: Handler
    }
    private lateinit var playerViewModel: PlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        playerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_player, container, false)

        val currentMusicTitle = rootView.findViewById<AppCompatTextView>(R.id.current_music_title)
        currentMusicTitle.text = binder.getCurrentMusicTitle()

        val currentMusicArtist = rootView.findViewById<AppCompatTextView>(R.id.current_music_artist)
        currentMusicArtist.text = binder.getCurrentMusicArtist()

        val playOrPause = rootView.findViewById<ImageView>(R.id.play_or_pause)
        playOrPause.setImageResource(R.drawable.ic_pause)
        playOrPause.setOnClickListener {
            if (binder.isPlaying()) {
                binder.pause()
                playOrPause.setImageResource(R.drawable.ic_play)
            } else {
                binder.play()
                playOrPause.setImageResource(R.drawable.ic_pause)
            }
        }

        val nextButton = rootView.findViewById<AppCompatImageButton>(R.id.nextButton)
        nextButton.setOnClickListener {
            binder.playNext()
            currentMusicArtist.text = binder.getCurrentMusicArtist()
            currentMusicTitle.text = binder.getCurrentMusicTitle()
            seekBar.max = binder.getPlayDuration()
            duration_text_view.text = formatTime(binder.getPlayDuration())
            play_or_pause.setImageResource(R.drawable.ic_pause)
        }

        val previousButton = rootView.findViewById<AppCompatImageButton>(R.id.previousButton)
        previousButton.setOnClickListener {
            binder.playPrevious()
            currentMusicArtist.text = binder.getCurrentMusicArtist()
            currentMusicTitle.text = binder.getCurrentMusicTitle()
            seekBar.max = binder.getPlayDuration()
            duration_text_view.text = formatTime(binder.getPlayDuration())
            play_or_pause.setImageResource(R.drawable.ic_pause)
        }

        val starButton = rootView.findViewById<AppCompatImageButton>(R.id.starButton)

        starButton.setOnClickListener {
            val music = binder.getCurrentMusic()
            val intent = Intent(MusicApplication.context, AddToSongListActivity::class.java)
                .apply {
                    putExtra("music", music)
                }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            MusicApplication.context.startActivity(intent)
        }

        val playMode = rootView.findViewById<AppCompatImageView>(R.id.play_mode)
        playMode.setOnClickListener {
            when (binder.getPlayMode()) {
                MusicApplication.TYPE_ORDER -> {
                    binder.setPlayMode(MusicApplication.TYPE_RANDOM)
                    Toast.makeText(context, "随机播放", Toast.LENGTH_SHORT).show()
                    playMode.setImageResource(R.drawable.ic_random)
                }
                MusicApplication.TYPE_RANDOM -> {
                    binder.setPlayMode(MusicApplication.TYPE_SINGLE)
                    Toast.makeText(context, "单曲循环", Toast.LENGTH_SHORT).show()
                    playMode.setImageResource(R.drawable.ic_singlerecycler)
                }
                MusicApplication.TYPE_SINGLE -> {
                    binder.setPlayMode(MusicApplication.TYPE_ORDER)
                    Toast.makeText(context, "顺序播放", Toast.LENGTH_SHORT).show()
                    playMode.setImageResource(R.drawable.ic_playrecycler)
                }
            }
        }

        val seekBar = rootView.findViewById<AppCompatSeekBar>(R.id.seekBar)
        val durationTextView = rootView.findViewById<TextView>(R.id.duration_text_view)
        val textView = rootView.findViewById<TextView>(R.id.text_view)
        val progressTextView = rootView.findViewById<TextView>(R.id.progress_text_view)
        if (binder.isCurrentMusicNull()){
            seekBar.isEnabled = false
            textView.visibility = INVISIBLE

        }
        else if (binder.isPlaying()){
            seekBar.isEnabled = true
            seekBar.max = binder.getPlayDuration()
            seekBar.progress = binder.getPlayProgress()
            textView.visibility = VISIBLE
            progressTextView.text = formatTime(binder.getPlayProgress())
            durationTextView.text = formatTime(binder.getPlayDuration())
        }

        val task = MyTimerTask()

        Timer().schedule(task, 1000, 1000)

        binder.getPlayer().setOnCompletionListener {
            if (binder.getPlayMode() != MusicApplication.TYPE_SINGLE){
                binder.playNext()
            }else{
                binder.getPlayer().seekTo(0)
                binder.getPlayer().start()
            }
            durationTextView.text = formatTime(binder.getPlayDuration())
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
                progressTextView.text = formatTime(binder.getPlayProgress())
            }
        })

        handler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                when(msg.what){
                    updateText -> progress_text_view.text = formatTime(binder.getPlayProgress())
                    1 -> {
                        play_or_pause.setImageResource(R.drawable.ic_play)
                        durationTextView.text = formatTime(binder.getPlayDuration())
                        currentMusicArtist.text = binder.getCurrentMusicArtist()
                        currentMusicTitle.text = binder.getCurrentMusicTitle()
                        seekBar.max = binder.getPlayDuration()
                        progress_text_view.text = formatTime(binder.getPlayProgress())
                    }
                    2 -> {
                        play_or_pause.setImageResource(R.drawable.ic_pause)
                        durationTextView.text = formatTime(binder.getPlayDuration())
                        currentMusicArtist.text = binder.getCurrentMusicArtist()
                        currentMusicTitle.text = binder.getCurrentMusicTitle()
                        seekBar.max = binder.getPlayDuration()
                        progress_text_view.text = formatTime(binder.getPlayProgress())
                    }
                }
            }
        }
        return rootView
    }


    inner class MyTimerTask() : TimerTask() {
        override fun run() {
            if (!binder.isCurrentMusicNull() && seekBar != null){
                val msg = Message()
                seekBar.progress =  binder.getPlayProgress()
                msg.what = updateText
                handler.sendMessage(msg)
            }
        }
    }

    val updateText = 0

    fun formatTime(time: Int): String? {
        val dateFormat = SimpleDateFormat("mm:ss")
        return dateFormat.format(time)
    }

}