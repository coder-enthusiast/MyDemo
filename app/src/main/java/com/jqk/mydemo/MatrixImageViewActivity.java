package com.jqk.mydemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jqk.mydemo.view.matrix.MatrixImageView;

public class MatrixImageViewActivity extends AppCompatActivity {

    private MatrixImageView matrixImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriximageview);

    }
}
