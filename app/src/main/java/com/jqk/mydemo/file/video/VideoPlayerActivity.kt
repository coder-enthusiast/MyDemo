package com.jqk.mydemo.file.video

import android.os.Bundle
import android.widget.AbsSeekBar
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R
import com.jqk.mydemo.util.L
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class VideoPlayerActivity : AppCompatActivity() {
    lateinit var ijkPlayer: IjkPlayView
    lateinit var seekBar: SeekBar
    lateinit var nowTime: TextView
    lateinit var allTime: TextView

    var path = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videoplayer)
        ijkPlayer = findViewById(R.id.ijkview)
        seekBar = findViewById(R.id.seekBar)
        nowTime = findViewById(R.id.nowTime)
        allTime = findViewById(R.id.allTime)

        path = intent.getStringExtra("path")

        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")

        ijkPlayer.setPath(path)
        ijkPlayer.seekBar = seekBar

    }

    override fun onStop() {
        super.onStop()
        ijkPlayer.stop()
        IjkMediaPlayer.native_profileEnd()
    }
}