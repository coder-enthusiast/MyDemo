package com.jqk.mydemo.editimg;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class TestView extends AppCompatImageView {
    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("123", "onTouchEvent");
        return true;
    }
}
