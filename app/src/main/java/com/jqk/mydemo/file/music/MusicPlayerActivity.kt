package com.jqk.mydemo.file.music

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityMusicplayerBinding
import com.jqk.mydemo.file.music.media.AndroidMediaController
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class MusicPlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityMusicplayerBinding
    lateinit var mMediaController: AndroidMediaController
    var musicName = ""
    var path = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_musicplayer)
        binding.view = this

        musicName = intent.getStringExtra("musicName")
        path = intent.getStringExtra("path")
        binding.title.text = musicName

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")

        mMediaController = AndroidMediaController(this, false)
        binding.videoView.setMediaController(mMediaController)
        binding.videoView.setHudView(binding.hudView)
        binding.videoView.setVideoPath(path)
        binding.videoView.start()
    }

    fun back(view: View) {
        finish()
    }

    fun ps(view: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.stopPlayback()
        binding.videoView.release(true)
        binding.videoView.stopBackgroundPlay()
        IjkMediaPlayer.native_profileEnd()
    }
}