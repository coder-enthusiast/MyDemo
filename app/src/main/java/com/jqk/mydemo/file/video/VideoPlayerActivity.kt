package com.jqk.mydemo.file.video

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R
import com.jqk.mydemo.util.L
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class VideoPlayerActivity : AppCompatActivity() {
    lateinit var ijkPlayer: IjkPlayView
    lateinit var seekBar: SeekBar
    lateinit var currentTime: TextView
    lateinit var endTime: TextView
    lateinit var ps: ImageView

    var path = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videoplayer)
        ijkPlayer = findViewById(R.id.ijkview)
        seekBar = findViewById(R.id.seekBar)
        currentTime = findViewById(R.id.currentTime)
        endTime = findViewById(R.id.endTime)
        ps = findViewById(R.id.ps)

        path = intent.getStringExtra("path")

        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")

        ijkPlayer.setPath(path)
        ijkPlayer.seekBar = seekBar
        ijkPlayer.currentTime = currentTime
        ijkPlayer.endTime = endTime
        ijkPlayer.ps = ps

    }

    override fun onStop() {
        super.onStop()
        ijkPlayer.stop()
        IjkMediaPlayer.native_profileEnd()
    }
}