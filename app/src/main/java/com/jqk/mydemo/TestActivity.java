package com.jqk.mydemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jqk.mydemo.mvvm.LoginActivity;
import com.jqk.mydemo.view.RippleView;

public class TestActivity extends AppCompatActivity {

    private Button button, start, stop;
    private RippleView rippleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        button = findViewById(R.id.button);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        rippleView = findViewById(R.id.rippleView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestActivity.this, LoginActivity.class));
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rippleView.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rippleView.stop();
            }
        });

        rippleView = findViewById(R.id.rippleView);
        rippleView.setCircleNumber(10);
        rippleView.setColor("#459021");
        rippleView.setNewCircleTime(500);
        rippleView.start();
    }
}
