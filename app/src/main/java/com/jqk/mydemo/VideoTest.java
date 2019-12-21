package com.jqk.mydemo;

import android.os.Bundle;
import android.view.SurfaceView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jqk.commonlibrary.util.L;

import VideoHandle.EpEditor;
import VideoHandle.OnEditorListener;

public class VideoTest extends AppCompatActivity {
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        surfaceView = findViewById(R.id.surface_view);

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
        EpEditor.video2pic("/sdcard/DCIM/Camera/测试.mp4", "/sdcard/DCIM/Camera/%d.jpg", 100, 100, 1, new OnEditorListener() {
            @Override
            public void onSuccess() {
                L.d("处理完成");
            }

            @Override
            public void onFailure() {
                L.d("处理失败");
            }

            @Override
            public void onProgress(float progress) {
                L.d("progress = " + progress);
            }
        });

    }
}
