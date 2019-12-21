package com.jqk.mydemo.util;

import android.content.Context;
import android.media.MediaPlayer;

import com.jqk.commonlibrary.util.L;
import com.jqk.mydemo.R;

public class AudioPlayer {
    private volatile static AudioPlayer mInstance;

    private MediaPlayer mediaPlayer;

    public static AudioPlayer getInstance() {
        if (mInstance != null) {
            return mInstance;
        }
        synchronized (AudioPlayer.class) {
            if (mInstance == null)
                mInstance = new AudioPlayer();
        }
        return mInstance;
    }

    public void start(Context context, int type) {
        L.d("start");
        try {
            stop();
            if (type == 1) {
                mediaPlayer = MediaPlayer.create(context, R.raw.ldw_left);
            } else if (type == 2) {
                mediaPlayer = MediaPlayer.create(context, R.raw.ldw_right);
            }
            mediaPlayer.start();
            mediaPlayer.setLooping(true);

        } catch (IllegalStateException e) {
            L.d("e = " + e.toString());
            e.printStackTrace();
        }
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
