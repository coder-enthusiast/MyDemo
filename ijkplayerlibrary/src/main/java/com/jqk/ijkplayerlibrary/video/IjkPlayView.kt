package com.jqk.ijkplayerlibrary.video

import android.content.Context
import android.media.AudioManager
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.jqk.commonlibrary.util.L
import com.jqk.commonlibrary.util.ScreenUtil
import com.jqk.ijkplayerlibrary.R
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import tv.danmaku.ijk.media.player.IjkTimedText
import java.util.*

class IjkPlayView : FrameLayout {
    val WIDTH_NORMAL = 400

    var mediaPlayer: IMediaPlayer? = null
    var surfaceView: SurfaceView? = null
    var mGestureDetector: GestureDetector? = null

    var mPath = ""

    var mDragging: Boolean = false
    var bufferPercentage = 0
    var completion = true

    var seekBar: SeekBar? = null
    var currentTime: TextView? = null
    var endTime: TextView? = null
    var ps: ImageView? = null

    var isFull = false

    var fullScreenWidth = 0
    var fullScreenHeight = 0
    var normalScreenWidth = 0
    var normalScreenHeight = 0

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
        initGestureDetector()
    }

    fun initGestureDetector() {
        mGestureDetector = GestureDetector(context, mSimpleOnGestureListener)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mGestureDetector?.onTouchEvent(event)
        return true
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
                    L.d("surfaceChanged " + "width = " + width + " height = " + height)
                }

                override fun surfaceDestroyed(holder: SurfaceHolder?) {

                }

                override fun surfaceCreated(holder: SurfaceHolder?) {
                    bindSurfaceView()
                }
            })
            val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            it.layoutParams = layoutParams
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
//                    L.d("onProgressChanged")

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
            val screenHeight = ScreenUtil.getScreenHeight(context)
            val scale = width / height.toFloat()
            val scaleS = screenWidth / screenHeight.toFloat()

            L.d("scale = " + scale)
            L.d("scaleS = " + scaleS)
            L.d("screenWidth = " + screenWidth)
            L.d("screenHeight = " + screenHeight)
            L.d("width = " + width)
            L.d("height = " + height)

            fullScreenHeight = screenWidth
            fullScreenWidth = fullScreenHeight * width / height

            normalScreenHeight = WIDTH_NORMAL
            normalScreenWidth = normalScreenHeight * width / height

            L.d("fullScreenHeight = " + fullScreenHeight)
            L.d("fullScreenWidth = " + fullScreenWidth)
            L.d("normalScreenHeight = " + normalScreenHeight)
            L.d("normalScreenWidth = " + normalScreenWidth)

            layoutParams.width = normalScreenWidth
            layoutParams.height = normalScreenHeight

            requestLayout()
        }
    }

    val mTimedTextListener = object : IMediaPlayer.OnTimedTextListener {
        override fun onTimedText(mp: IMediaPlayer?, text: IjkTimedText?) {
        }
    }

    val mErrorListener = object : IMediaPlayer.OnErrorListener {
        override fun onError(mp: IMediaPlayer?, what: Int, extra: Int): Boolean {
            L.d("what = " + what)
            L.d("extra = " + extra)
            return true;
        }
    }

    val mSeekCompleteListener = object : IMediaPlayer.OnSeekCompleteListener {
        override fun onSeekComplete(mp: IMediaPlayer?) {

        }
    }

    val mSimpleOnGestureListener: GestureDetector.SimpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return super.onSingleTapConfirmed(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            // 双击全屏
            setFullScreen()
            return super.onDoubleTap(e)
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    fun setFullScreen() {
        if (isFull) {
            L.d("正常")
            val lp = layoutParams
            lp.width = normalScreenWidth
            lp.height = normalScreenHeight
            layoutParams = lp

            val layoutParams = LayoutParams(normalScreenWidth, normalScreenHeight)
            surfaceView?.layoutParams = layoutParams
            surfaceView?.requestLayout()

            requestLayout()

        } else {
            L.d("全屏")
            val lp = layoutParams
            lp.width = fullScreenWidth
            lp.height = fullScreenHeight
            layoutParams = lp

            val layoutParams = LayoutParams(fullScreenWidth, fullScreenHeight)
            surfaceView?.layoutParams = layoutParams
            surfaceView?.requestLayout()

            requestLayout()
        }

        listener?.onFullScreen(!isFull)
        isFull = !isFull
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