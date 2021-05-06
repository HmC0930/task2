package com.example.task2

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import com.example.task2.ui.player.PlayerFragment

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var mediaBrowserServiceCompat: MyMediaBrowserServiceCompat
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent= Intent(this,MyMediaBrowserServiceCompat::class.java)
        startService(intent)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_view)
        val appBarConfiguration = AppBarConfiguration(setOf(
        R.id.navigation_home))
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaBrowserServiceCompat.mMediaPlayer.stop()
        mediaBrowserServiceCompat.mMediaPlayer.release()
        mediaBrowserServiceCompat.onDestroy()
    }
}