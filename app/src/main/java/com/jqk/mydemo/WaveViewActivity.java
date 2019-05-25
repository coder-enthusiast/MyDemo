package com.jqk.mydemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jqk.mydemo.view.WaveView;

public class WaveViewActivity extends AppCompatActivity {

    private WaveView waveView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waveview);
        waveView = findViewById(R.id.waveView);

        waveView.setPercentage(5);
    }
}
