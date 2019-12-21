package com.jqk.ijkplayerlibrary.video

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import android.media.MediaMetadataRetriever
import com.jqk.commonlibrary.util.L
import com.jqk.ijkplayerlibrary.R


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

        if (path.endsWith(".mp3") || path.endsWith(".ogg")) {
            val mmr = MediaMetadataRetriever()
            mmr.setDataSource(path)
            val title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            L.d("title = " + title)
            val album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
            L.d("album = " + album)
            val mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)
            L.d("mime = " + mime)
            val artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            L.d("artist = " + artist)
            val duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) // ms
            L.d("duration = " + duration)
            val bitrate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE) // bit/s api >= 14
            L.d("bitrate = " + bitrate)
            val date = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE)
            L.d("date = " + date)
        }

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
//        ijkPlayer.stop()
        IjkMediaPlayer.native_profileEnd()
    }
}