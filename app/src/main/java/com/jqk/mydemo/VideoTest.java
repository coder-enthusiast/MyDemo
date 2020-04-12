package com.jqk.mydemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jqk.commonlibrary.util.L;

import org.bytedeco.javacv.AndroidFrameConverter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;

//import VideoHandle.EpEditor;
//import VideoHandle.OnEditorListener;

public class VideoTest extends AppCompatActivity {
    private SurfaceView surfaceView;
    private ImageView imageView;
    private Bitmap frameBitmap;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1000) {
                if (frameBitmap != null) {
                    imageView.setImageBitmap(frameBitmap);
                }

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        surfaceView = findViewById(R.id.surface_view);
        imageView = findViewById(R.id.imageView);

//        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this);
//        player.setVideoSurfaceView(surfaceView);
//
//        // Produces DataSource instances through which media data is loaded.
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
//                Util.getUserAgent(this, "yourApplicationName"));
//// This is the MediaSource representing the media to be played.
//        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(Uri.parse("/sdcard/DCIM/Camera/测试.mp4"));
//// Prepare the player with the source.
//        player.prepare(videoSource);
//
//        player.setPlayWhenReady(true);
//
//        player.addListener(new Player.EventListener() {
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                switch (playbackState) {
//                    case Player.STATE_IDLE: // 播放器停止时的状态以及播放失败时的状态
//                        L.d("STATE_IDLE");
//                        break;
//                    case Player.STATE_BUFFERING: // 加载中
//                        L.d("STATE_BUFFERING");
//                        break;
//                    case Player.STATE_READY: // 准备好
//                        L.d("STATE_READY");
//                        player.setPlayWhenReady(false);
//                        break;
//                    case Player.STATE_ENDED: // 播放完
//                        L.d("STATE_ENDED");
//                        break;
//                }
//            }
//
//            @Override
//            public void onPlayerError(ExoPlaybackException error) {
//
//            }
//        });
//
//        player.addVideoListener(new VideoListener() {
//            @Override
//            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
//                L.d("width = " + width);
//                L.d("height = " + height);
//
//                int screenWidth = ScreenUtil.INSTANCE.getScreenWidth(VideoTest.this);
//                float h = (float) screenWidth / width * height;
//                int statusH = ScreenUtil.INSTANCE.getStatusBarHeight(VideoTest.this);
//                float maxHeight = ScreenUtil.INSTANCE.getDensity(VideoTest.this) * 200;
//                L.d("screenWidth = " + screenWidth);
//                L.d("width = " + width);
//                L.d("height = " + height);
//                L.d("maxHeight = " + maxHeight);
//                L.d("h = " + h);
//
//                if (h > maxHeight) {
//
//                    float w = maxHeight / h * screenWidth;
//
//                    L.d("w = " + w);
//
//                    ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
//                    lp.width = (int) w;
//                    lp.height = (int) maxHeight;
//                    surfaceView.setLayoutParams(lp);
//                } else {
//                    ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
//                    lp.width = screenWidth;
//                    lp.height = (int) h;
//                    surfaceView.setLayoutParams(lp);
//                }
//            }
//
//            @Override
//            public void onSurfaceSizeChanged(int width, int height) {
//                L.d("width1 = " + width);
//                L.d("height1 = " + height);
//            }
//
//            @Override
//            public void onRenderedFirstFrame() {
//
//            }
//        });


//        EpVideo epVideo = new EpVideo("/sdcard/DCIM/Camera/测试.mp4");
//
////一个参数为剪辑的起始时间，第二个参数为持续时间,单位：秒
//        epVideo.clip(1,2);//从第一秒开始，剪辑两秒
//
//        //输出选项，参数为输出文件路径(目前仅支持mp4格式输出)
//        EpEditor.OutputOption outputOption = new EpEditor.OutputOption("/sdcard/DCIM/Camera/123456.mp4");
//        outputOption.frameRate = 30;//输出视频帧率,默认30
//        outputOption.bitRate = 10;//输出视频码率,默认10
//        EpEditor.exec(epVideo, outputOption, new OnEditorListener() {
//            @Override
//            public void onSuccess() {
//                L.d("处理完成");
//            }
//
//            @Override
//            public void onFailure() {
//                L.d("处理失败");
//            }
//
//            @Override
//            public void onProgress(float progress) {
//                //这里获取处理进度
//                L.d("progress = " + progress);
//            }
//        });

        // 获取指定时间的缩略图
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<String> paths = new ArrayList<>();
////                for (int i = 0; i < 30; i++) {
//                    paths.add("/sdcard/DCIM/Camera/ " + "1" + "测试.jpg");
//
//                    String cmd = "-i /sdcard/DCIM/Camera/测试.mp4 -y -f image2 -ss " + "1" + " -t 0.001 -s 350x240 " + "/sdcard/DCIM/Camera/" + "1" + "测试.jpg";
//
//                    EpEditor.execCmd(cmd, 0, new OnEditorListener() {
//                        @Override
//                        public void onSuccess() {
//                            L.d("处理完成");
//                        }
//
//                        @Override
//                        public void onFailure() {
//                            L.d("处理失败");
//                        }
//
//                        @Override
//                        public void onProgress(float progress) {
//                            L.d("progress = " + progress);
//                        }
//                    });
//                }
////            }
//        }).start();

        ////参数分别是视频路径,输出路径（路径用集合的形式，比如pic%03d.jpg,支持jpg和png两种图片格式）,输出图片的宽度，输出图片的高度，每秒输出图片数量（2的话就是每秒2张，0.5f的话就是每两秒一张）
//        EpEditor.video2pic("/sdcard/DCIM/Camera/测试.mp4", "/sdcard/DCIM/Camera/%d.jpg", 100, 100, 1, new OnEditorListener() {
//            @Override
//            public void onSuccess() {
//                L.d("处理完成");
//            }
//
//            @Override
//            public void onFailure() {
//                L.d("处理失败");
//            }
//
//            @Override
//            public void onProgress(float progress) {
//                L.d("progress = " + progress);
//            }
//        });

        File file = new File("/sdcard/DCIM/Camera/测试.mp4");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    fetchPic(file, "", 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public void fetchPic(File file, String framefile, int second) throws Exception {
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
        ff.start();
        int lenght = ff.getLengthInVideoFrames();
        long times = ff.getLengthInTime() / (1000 * 1000);
        L.d("times = " + times);
        int i = 0;
        Frame frame = null;
        second = 0;
        while (i < lenght) {
            L.d("视频帧 = " + i);
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            frame = ff.grabImage();

            if (i == 500) {
                frameBitmap = new AndroidFrameConverter().convert(frame);
                handler.sendEmptyMessage(1000);
            }
            i++;
        }
        ff.stop();
    }

    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }


    /**
     * 获取视频时长，单位为秒
     *
     * @param file
     * @return 时长（s）
     */
    public static Long getVideoTime(File file) {
        Long times = 0L;
        try {
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file);
            ff.start();
            times = ff.getLengthInTime() / (1000 * 1000);
            ff.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
}
