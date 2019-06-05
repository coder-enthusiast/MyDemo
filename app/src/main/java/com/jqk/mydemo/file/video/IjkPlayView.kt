package com.jqk.mydemo.file.video

import android.content.Context
import android.media.AudioManager
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout
import com.jqk.mydemo.util.L
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class IjkPlayView : FrameLayout {
    /**
     * 由ijkplayer提供，用于播放视频，需要给他传入一个surfaceView
     */
    private var mMediaPlayer: IMediaPlayer? = null

    /**
     * 视频文件地址
     */
    private var mPath = ""

    private var surfaceView: SurfaceView? = null

    var c: Context? = null

    constructor(context: Context) : super(context) {
        initVideoView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initVideoView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initVideoView(context)
    }

    fun initVideoView(context: Context) {
        c = context

        //获取焦点，不知道有没有必要~。~
        isFocusable = true
    }

    /**
     * 设置视频地址。
     * 根据是否第一次播放视频，做不同的操作。
     *
     * @param path the path of the video.
     */
    fun setVideoPath(path: String) {
        if (TextUtils.equals("", mPath)) {
            //如果是第一次播放视频，那就创建一个新的surfaceView
            mPath = path
            createSurfaceView()
        } else {
            //否则就直接load
            mPath = path
            initPlayer()
        }
    }

    /**
     * 新建一个surfaceview
     */
    private fun createSurfaceView() {
        //生成一个新的surface view
        surfaceView = SurfaceView(c)
        surfaceView!!.getHolder().addCallback(LmnSurfaceCallback())
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER)
        surfaceView!!.setLayoutParams(layoutParams)
        this.addView(surfaceView)
    }

    /**
     * surfaceView的监听器
     */
    private inner class LmnSurfaceCallback : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {

        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            //surfaceview创建成功后，加载视频
            initPlayer()
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {}
    }

    /**
     * 加载视频
     */
    private fun initPlayer() {
        L.d("load")
        createPlayer()
        mMediaPlayer!!.setDataSource(mPath)
        L.d("设置视图")
        mMediaPlayer!!.setDisplay(surfaceView!!.getHolder())
        mMediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mMediaPlayer!!.setScreenOnWhilePlaying(true)
        mMediaPlayer!!.prepareAsync()
    }

    /**
     * 创建一个新的player
     */
    private fun createPlayer() {
        mMediaPlayer = IjkMediaPlayer()

        if (videoPlayerListener != null) {
            mMediaPlayer!!.setOnPreparedListener(videoPlayerListener)
            mMediaPlayer!!.setOnInfoListener(videoPlayerListener)
            mMediaPlayer!!.setOnSeekCompleteListener(videoPlayerListener)
            mMediaPlayer!!.setOnBufferingUpdateListener(videoPlayerListener)
            mMediaPlayer!!.setOnErrorListener(videoPlayerListener)
        }
    }


    fun start() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.start()
        }
    }

    fun release() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.reset()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    fun pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.pause()
        }
    }

    fun stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
        }
    }


    fun reset() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.reset()
        }
    }


    fun getDuration(): Long {
        return if (mMediaPlayer != null) {
            mMediaPlayer!!.getDuration()
        } else {
            0
        }
    }


    fun getCurrentPosition(): Long {
        return if (mMediaPlayer != null) {
            mMediaPlayer!!.getCurrentPosition()
        } else {
            0
        }
    }


    fun seekTo(l: Long) {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.seekTo(l)
        }
    }


    private var videoPlayerListener: VideoPlayerListener? = null

    fun setVideo(videoPlayerListener: VideoPlayerListener) {
        this.videoPlayerListener = videoPlayerListener


    }

    val mBufferingUpdateListener = object : IMediaPlayer.OnBufferingUpdateListener {
        override fun onBufferingUpdate(mp: IMediaPlayer?, percent: Int) {

        }
    }

    val mCompletionListener = object: IMediaPlayer.OnCompletionListener {
        override fun onCompletion(mp: IMediaPlayer?) {

        }
    }

    val PreparedListener = object : IMediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: IMediaPlayer?) {

        }
    }



    interface VideoPlayerListener :
            IMediaPlayer.OnBufferingUpdateListener,
            IMediaPlayer.OnCompletionListener,
            IMediaPlayer.OnPreparedListener,
            IMediaPlayer.OnInfoListener,
            IMediaPlayer.OnVideoSizeChangedListener,
            IMediaPlayer.OnErrorListener,
            IMediaPlayer.OnSeekCompleteListener {

    }
}