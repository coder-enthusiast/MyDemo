package com.jqk.mydemo

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.util.L

class SoundPoolActivity : AppCompatActivity() {
    lateinit var mAudioManager: AudioManager

    val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            msg?.let {
                if (it.what == 1000) {
                    mAudioManager.abandonAudioFocus(null)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        startSoundPool()
    }

    fun startSoundPool() {
        var n = 1

        val soundPool = SoundPool(1, AudioManager.STREAM_ALARM, 5)//构建对象
        object : Thread(object : Runnable {
            override fun run() {
                while (true) {
                    when (n) {
                        1 // 车道左偏离
                        -> {
                            L.d("左边")
                            mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                            soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
                                soundPool.play(sampleId, 1f, 1f, 1, 0, 1f)//播放
                                Thread.sleep(1000)
                                soundPool.play(sampleId, 1f, 1f, 1, 0, 1f)//播放
                                L.d("status左 = " + status)
                                handler.sendEmptyMessageDelayed(1000, 3000)
                            }
                            soundPool.load(this@SoundPoolActivity, R.raw.ldw_left, 1)//加载资源
                        }
                        2 // 车道右偏离
                        -> {
                            L.d("右边")
                            mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                            soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
                                soundPool.play(sampleId, 1f, 1f, 1, 0, 1f)//播放
                                L.d("status右 = " + status)
                                handler.sendEmptyMessageDelayed(1000, 3000)
                            }
                            soundPool.load(this@SoundPoolActivity, R.raw.ldw_right, 1)//加载资源
                        }
                    }
                    if (n == 1) {
                        n = 2
                    } else {
                        n = 1
                    }
                    Thread.sleep(5000)
                }
            }
        }) {

        }.start()
    }
}