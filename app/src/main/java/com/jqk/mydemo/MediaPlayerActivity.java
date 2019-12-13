package com.jqk.mydemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jqk.mydemo.util.AudioPlayer;

public class MediaPlayerActivity extends AppCompatActivity {
    private Button start;
    private Button stop;
    private Button cycle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        cycle = findViewById(R.id.cycle);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioPlayer.getInstance().start(MediaPlayerActivity.this, 1);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioPlayer.getInstance().stop();
            }
        });

        cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
