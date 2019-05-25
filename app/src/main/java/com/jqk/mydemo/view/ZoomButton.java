package com.jqk.mydemo.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class ZoomButton extends AppCompatButton {

    private float viewWidth;
    private float viewHeight;
    private float nowWidth;
    private float nowHeight;
    private float scale;
    private float zoomScale = 0.1f;
    private ValueAnimator small, big;

    private boolean isFirst = true;

    public ZoomButton(Context context) {
        super(context);
    }

    public ZoomButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isFirst) {
            viewWidth = getMeasuredWidth();
            viewHeight = getMeasuredHeight();
            nowWidth = viewHeight;
            scale = viewHeight / viewWidth;
            isFirst = false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                zoomSmall();
                zoomBig();
                big.end();
                small.start();
                break;
            case MotionEvent.ACTION_UP:
                zoomSmall();
                zoomBig();
                small.end();
                big.start();
                break;
        }

        return super.onTouchEvent(event);
    }

    public void zoomSmall() {
        Log.d("123", "viewWidth * zoomScale = " + viewWidth * zoomScale);
        small = ValueAnimator.ofFloat(0, viewWidth * zoomScale);
        small.setDuration(100);
        small.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float i = (float) animation.getAnimatedValue();

                Log.d("123", "i = " + i);
                nowWidth = viewWidth - i;
                nowHeight = (int) (scale * nowWidth);
                zoomAnim(viewWidth, nowWidth, viewHeight, nowHeight);
            }
        });
    }

    public void zoomBig() {
        big = ValueAnimator.ofFloat(nowWidth, viewWidth);
        big.setDuration(100);
        big.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float i = (float) animation.getAnimatedValue();

                Log.d("123", "i = " + i);
                nowWidth = i;
                nowHeight = (int) (scale * nowWidth);
                zoomAnim(viewWidth, nowWidth, viewHeight, nowHeight);
            }
        });
    }

    public void zoomAnim(float viewWidth, float nowWidth, float viewHeight, float nowHeight) {

        float zoomWidth = nowWidth / viewWidth;
        float zoomHeight = nowHeight / viewHeight;

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", zoomWidth, zoomWidth);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", zoomHeight, zoomHeight);
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.setDuration(0);
        animatorSet.start();
    }
}
