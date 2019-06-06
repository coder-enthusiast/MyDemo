package com.jqk.mydemo.file.video

import android.content.Context
import android.media.AudioManager
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import com.jqk.mydemo.util.L
import com.jqk.mydemo.util.ScreenUtil
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import tv.danmaku.ijk.media.player.IjkTimedText

class IjkPlayView : FrameLayout {
    lateinit var mediaPlayer: IMediaPlayer

    lateinit var surfaceView: SurfaceView

    var mPath = ""

    var seekBar: SeekBar? = null
    var nowTime: TextView? = null
    var allTime: TextView? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    fun initView() {
        initPlayer()
        initSurfaceView()
    }

    fun initSurfaceView() {
        //生成一个新的surface view
        surfaceView = SurfaceView(context)
        surfaceView.getHolder().addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {

            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                bindSurfaceView()
            }
        })
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        surfaceView.setLayoutParams(layoutParams)
        addView(surfaceView)
    }

    fun initPlayer() {
        L.d("load")
        mediaPlayer = IjkMediaPlayer()

        mediaPlayer.setOnPreparedListener(mPreparedListener)
        mediaPlayer.setOnInfoListener(mInfoListener)
        mediaPlayer.setOnSeekCompleteListener(mSeekCompleteListener)
        mediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener)
        mediaPlayer.setOnErrorListener(mErrorListener)
        mediaPlayer.setOnVideoSizeChangedListener(mVideoSizeChangedListener)
        mediaPlayer.setOnCompletionListener(mCompletionListener)
        mediaPlayer.setOnTimedTextListener(mTimedTextListener)
    }

    fun initSeekBar() {
        seekBar?.let {
            it.max = getDuration() as Int
            it.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    L.d("onProgressChanged")
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    L.d("onStartTrackingTouch")
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    L.d("onStopTrackingTouch")
                }
            })
        }
    }

    val mBufferingUpdateListener = object : IMediaPlayer.OnBufferingUpdateListener {
        override fun onBufferingUpdate(mp: IMediaPlayer?, percent: Int) {
//            L.d("percent = " + percent)
        }
    }

    val mCompletionListener = object : IMediaPlayer.OnCompletionListener {
        override fun onCompletion(mp: IMediaPlayer?) {
            L.d("onCompletion")
        }
    }

    val mPreparedListener = object : IMediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: IMediaPlayer?) {
            L.d("onPrepared")

        }
    }

    val mInfoListener = object : IMediaPlayer.OnInfoListener {
        override fun onInfo(mp: IMediaPlayer?, what: Int, extra: Int): Boolean {
            L.d("onInfo")
            return false
        }
    }

    val mVideoSizeChangedListener = object : IMediaPlayer.OnVideoSizeChangedListener {
        override fun onVideoSizeChanged(mp: IMediaPlayer?, width: Int, height: Int, sar_num: Int, sar_den: Int) {
            val screenWidth = ScreenUtil.getScreenWidth(context)

            val h = screenWidth / width * height

            L.d("h = " + h)

            val lp = layoutParams
            lp.width = screenWidth
            lp.height = h
            layoutParams = lp
            requestLayout()
        }
    }

    val mTimedTextListener = object : IMediaPlayer.OnTimedTextListener {
        override fun onTimedText(mp: IMediaPlayer?, text: IjkTimedText?) {
        }
    }

    val mErrorListener = object : IMediaPlayer.OnErrorListener {
        override fun onError(mp: IMediaPlayer?, what: Int, extra: Int): Boolean {
            return false
        }
    }

    val mSeekCompleteListener = object : IMediaPlayer.OnSeekCompleteListener {
        override fun onSeekComplete(mp: IMediaPlayer?) {

        }
    }

    fun setPath(path: String) {
        mPath = path
        mediaPlayer.setDataSource(mPath)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setScreenOnWhilePlaying(true)
        mediaPlayer.prepareAsync()
    }

    fun bindSurfaceView() {
        mediaPlayer.setDisplay(surfaceView.getHolder())
    }

    fun start() {
        if (mediaPlayer != null) {
            mediaPlayer.start()
        }
    }

    fun release() {
        if (mediaPlayer != null) {
            mediaPlayer.reset()
            mediaPlayer.release()
        }
    }

    fun pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause()
        }
    }

    fun stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop()
        }
    }


    fun reset() {
        if (mediaPlayer != null) {
            mediaPlayer.reset()
        }
    }


    fun getDuration(): Long {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration()
        } else {
            return 0
        }
    }

    fun getCurrentPosition(): Long {
        return if (mediaPlayer != null) {
            mediaPlayer.getCurrentPosition()
        } else {
            0
        }
    }

    fun seekTo(l: Long) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(l)
        }
    }

    var listener: VideoPlayerListener? = null

    fun setOnListener(videoPlayerListener: VideoPlayerListener) {
        listener = videoPlayerListener
    }
}