package com.example.task2.ui.player

import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media.MediaBrowserServiceCompat

class PlayerViewModel : ViewModel() {
    var maxLivadata = MutableLiveData<Int>()
    var progressLiveData = MutableLiveData<Int>()


}