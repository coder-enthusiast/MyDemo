package com.jqk.mydemo.anji;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jqk.mydemo.R;
import com.jqk.mydemo.view.ZoomButton;

public class AnjiActivity extends AppCompatActivity {

    private ZoomButton button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anji);

        button = findViewById(R.id.zoomView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 动画跳转操作
                // 按钮动画执行完再跳转
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(AnjiActivity.this, R.anim.activity_enter, R.anim.activity_exit);
                        Intent intent = new Intent();
                        intent.setClass(AnjiActivity.this, SecondActivity.class);
                        ActivityCompat.startActivity(AnjiActivity.this, intent, compat.toBundle());
                    }
                }, 100);
            }
        });
    }
}
