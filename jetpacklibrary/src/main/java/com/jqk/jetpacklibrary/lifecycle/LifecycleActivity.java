package com.jqk.jetpacklibrary.lifecycle;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class LifecycleActivity extends AppCompatActivity {

    private MyLocationListener myLocationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myLocationListener = new MyLocationListener(this, getLifecycle(), new OnDataCallback() {
            @Override
            public void onSuccess(Object data) {
                // 判断用户的状态
                if (true) {
                    myLocationListener.enable();
                }

            }

            @Override
            public void onError() {

            }
        });
    }
}
