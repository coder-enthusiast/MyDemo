package com.jqk.mydemo.file.video

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R
import com.jqk.mydemo.util.L
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class VideoPlayerActivity : AppCompatActivity() {
    lateinit var ijkPlayer: IjkPlayView
    var path = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videoplayer)
        ijkPlayer = findViewById(R.id.ijkview)
        path = intent.getStringExtra("path")

        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")
    }

    override fun onStop() {
        super.onStop()
        ijkPlayer.stop()
        IjkMediaPlayer.native_profileEnd()
    }
}