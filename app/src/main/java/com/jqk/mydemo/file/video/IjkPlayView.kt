package com.jqk.mydemo.file.video

import android.content.Context
import android.media.AudioManager
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.*
import com.jqk.mydemo.R
import com.jqk.mydemo.util.L
import com.jqk.mydemo.util.ScreenUtil
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import tv.danmaku.ijk.media.player.IjkTimedText
import java.util.*
import java.util.function.IntToLongFunction

class IjkPlayView : FrameLayout {
    var mediaPlayer: IMediaPlayer? = null

    var surfaceView: SurfaceView? = null

    val v: MediaController? = null

    var mPath = ""

    var mDragging: Boolean = false
    var bufferPercentage = 0
    var completion = true

    var seekBar: SeekBar? = null
    var currentTime: TextView? = null
    var endTime: TextView? = null
    var ps: ImageView? = null

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
        initPs()
    }

    fun initPs() {
        ps?.let {
            if (completion) {
                it.setImageResource(R.drawable.icon_start)
            } else {
                it.setImageResource(R.drawable.icon_pause)
            }
            it.setOnClickListener {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        pause()
                    } else {
                        start()
                        post(progressRun)
                    }
                }
            }
        }
    }

    fun initSurfaceView() {
        //生成一个新的surface view
        surfaceView = SurfaceView(context)
        surfaceView?.let {
            it.getHolder().addCallback(object : SurfaceHolder.Callback {
                override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

                }

                override fun surfaceDestroyed(holder: SurfaceHolder?) {

                }

                override fun surfaceCreated(holder: SurfaceHolder?) {
                    bindSurfaceView()
                }
            })
            val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            it.setLayoutParams(layoutParams)
            addView(it)
        }


    }

    fun initPlayer() {
        L.d("load")
        mediaPlayer = IjkMediaPlayer()

        mediaPlayer?.let {
            it.setOnPreparedListener(mPreparedListener)
            it.setOnInfoListener(mInfoListener)
            it.setOnSeekCompleteListener(mSeekCompleteListener)
            it.setOnBufferingUpdateListener(mBufferingUpdateListener)
            it.setOnErrorListener(mErrorListener)
            it.setOnVideoSizeChangedListener(mVideoSizeChangedListener)
            it.setOnCompletionListener(mCompletionListener)
            it.setOnTimedTextListener(mTimedTextListener)
        }
    }

    fun initSeekBar() {
        L.d("初始化seekbar")
        seekBar?.let {
            currentTime?.text = "00:00"
            endTime?.text = buildTimeMilli(getDuration())
            it.max = 1000
            it.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    L.d("onProgressChanged")

                    if (!fromUser) {
                        return
                    }

                    val duration = mediaPlayer!!.getDuration()
                    val newposition = duration * progress / 1000L
                    mediaPlayer?.seekTo(newposition)
                    currentTime?.setText(buildTimeMilli(newposition))
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    L.d("onStartTrackingTouch")
                    mDragging = true
                    removeCallbacks(progressRun)
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    L.d("onStopTrackingTouch")
                    mDragging = false
                    completion = false
                    setProgress()
                    post(progressRun)
                }
            })
        }
    }

    val mBufferingUpdateListener = object : IMediaPlayer.OnBufferingUpdateListener {
        override fun onBufferingUpdate(mp: IMediaPlayer?, percent: Int) {
//            L.d("percent = " + percent)
            bufferPercentage = percent
        }
    }

    val mCompletionListener = object : IMediaPlayer.OnCompletionListener {
        override fun onCompletion(mp: IMediaPlayer?) {
            // 播放结束
            L.d("onCompletion")
            completion = true
            initPs()
        }
    }

    val mPreparedListener = object : IMediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: IMediaPlayer?) {
            // 资源准备好
            L.d("onPrepared")
            completion = false
            initPs()
            initSeekBar()
            post(progressRun)
        }
    }

    val mInfoListener = object : IMediaPlayer.OnInfoListener {
        override fun onInfo(mp: IMediaPlayer?, what: Int, extra: Int): Boolean {
//            L.d("onInfo")
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
        mediaPlayer?.let {
            it.setDataSource(mPath)
            it.setAudioStreamType(AudioManager.STREAM_MUSIC)
            it.setScreenOnWhilePlaying(true)
            it.prepareAsync()
        }
    }

    fun bindSurfaceView() {
        mediaPlayer?.setDisplay(surfaceView?.getHolder())
    }

    fun start() {
        mediaPlayer?.let {
            it.start()
            completion = false
            initPs()
        }
    }

    fun release() {
        mediaPlayer?.let {
            it.reset()
            it.release()
        }
    }

    fun pause() {
        mediaPlayer?.let {
            it.pause()
            completion = true
            initPs()
        }
    }

    fun stop() {
        mediaPlayer?.let {
            it.stop()
        }
    }


    fun reset() {
        mediaPlayer?.let {
            it.reset()
        }
    }

    fun getDuration(): Long {
        mediaPlayer?.let {
            return it.getDuration()
        }
        return 0
    }

    fun getCurrentPosition(): Long {
        mediaPlayer?.let {
            return it.getCurrentPosition()
        }
        return 0
    }

    fun seekTo(l: Long) {
        mediaPlayer?.let {
            it.seekTo(l)
        }
    }

    fun buildTimeMilli(duration: Long): String {
        val total_seconds = duration / 1000
        val hours = total_seconds / 3600
        val minutes = total_seconds % 3600 / 60
        val seconds = total_seconds % 60
        if (duration <= 0) {
            return "00:00"
        }
        return if (hours >= 100) {
            String.format(Locale.US, "%d:%02d:%02d", hours, minutes, seconds)
        } else if (hours > 0) {
            String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.US, "%02d:%02d", minutes, seconds)
        }
    }

    val progressRun = object : Runnable {
        override fun run() {
            val pos = setProgress()
            if (!mDragging && mediaPlayer!!.isPlaying()) {
                postDelayed(this, (1000 - pos % 1000))
            }
        }
    }

    private fun setProgress(): Long {
        if (mediaPlayer == null || mDragging) {
            return 0
        }
        val position = mediaPlayer!!.getCurrentPosition()
        val duration = mediaPlayer!!.getDuration()
        if (duration > 0) {
            // use long to avoid overflow
            val pos = 1000L * position / duration
            seekBar?.setProgress(pos.toInt())
        }
        val percent = bufferPercentage
        seekBar?.setSecondaryProgress(percent * 10)
        if (completion) {
            currentTime?.setText(buildTimeMilli(duration))
        } else {
            currentTime?.setText(buildTimeMilli(position))
        }

        return position
    }


    var listener: VideoPlayerListener? = null

    fun setOnListener(videoPlayerListener: VideoPlayerListener) {
        listener = videoPlayerListener
    }
}