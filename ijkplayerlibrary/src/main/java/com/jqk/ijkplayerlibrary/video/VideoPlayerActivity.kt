package com.jqk.ijkplayerlibrary.video

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.view.OrientationEventListener
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jqk.commonlibrary.util.L
import com.jqk.ijkplayerlibrary.R
import tv.danmaku.ijk.media.player.IjkMediaPlayer


class VideoPlayerActivity : AppCompatActivity() {
    lateinit var ijkPlayer: IjkPlayView
    lateinit var seekBar: SeekBar
    lateinit var currentTime: TextView
    lateinit var endTime: TextView
    lateinit var ps: ImageView

    var path = ""

    lateinit var orientationEventListener: OrientationEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videoplayer)
        ijkPlayer = findViewById(R.id.ijkview)
        seekBar = findViewById(R.id.seekBar)
        currentTime = findViewById(R.id.currentTime)
        endTime = findViewById(R.id.endTime)
        ps = findViewById(R.id.ps)

        orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                L.d("onOrientationChanged: $orientation")
                if (orientation == ORIENTATION_UNKNOWN) {
                    return  // 手机平放时，检测不到有效的角度
                }
                // 只检测是否有四个角度的改变
                if (orientation > 350 || orientation < 10) { // 0度：手机默认竖屏状态（home键在正下方）
                    L.d("下")
                } else if (orientation > 80 && orientation < 100) { // 90度：手机顺时针旋转90度横屏（home建在左侧）
                    L.d("左")
                } else if (orientation > 170 && orientation < 190) { // 180度：手机顺时针旋转180度竖屏（home键在上方）
                    L.d("上")
                } else if (orientation > 260 && orientation < 280) { // 270度：手机顺时针旋转270度横屏，（home键在右侧）
                    L.d("右")
                }
            }
        }

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

        ijkPlayer.setOnListener(object : VideoPlayerListener {
            override fun onFullScreen(isFull: Boolean) {
                setFullScreen(isFull)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        ijkPlayer.stop()
        IjkMediaPlayer.native_profileEnd()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig?.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }

    fun setFullScreen(isFull: Boolean) {
        if (isFull) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            hideSystemUI()
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            showSystemUI()
        }
    }


    fun hideSystemUI() {
        window.decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    fun showSystemUI() {
        window.decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}