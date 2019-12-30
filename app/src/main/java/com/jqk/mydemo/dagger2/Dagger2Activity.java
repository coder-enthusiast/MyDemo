package com.jqk.mydemo.dagger2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.jqk.mydemo.R;
import com.jqk.mydemo.base.BaseActivity;
import com.jqk.mydemo.dagger2.login.LoginActivity;

public class Dagger2Activity extends BaseActivity {
    private Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);

        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity(LoginActivity.class);
            }
        });
    }
}
