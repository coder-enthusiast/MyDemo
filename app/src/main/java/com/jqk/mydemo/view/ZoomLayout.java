package com.jqk.mydemo.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import io.reactivex.annotations.Nullable;

public class ZoomLayout extends FrameLayout {

    private float viewWidth;
    private float viewHeight;
    private float nowWidth;
    private float nowHeight;
    private float scale;
    private float zoomScale = 0.1f;
    private ValueAnimator smail, big;

    private boolean isFirst = true;

    private boolean isActionUp = false;

    public ZoomLayout(Context context) {
        super(context);
    }

    public ZoomLayout(Context context, @io.reactivex.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
                zoomsmail();
                zoomBig();
                big.end();
                smail.start();
                break;
            case MotionEvent.ACTION_UP:
                isActionUp = true;
                zoomsmail();
                zoomBig();
                smail.end();
                big.start();
                break;
            case MotionEvent.ACTION_CANCEL:
                zoomsmail();
                zoomBig();
                smail.end();
                big.start();
                break;
        }

        return true;
    }

    public void zoomsmail() {
        Log.d("123", "viewWidth * zoomScale = " + viewWidth * zoomScale);
        smail = ValueAnimator.ofFloat(0, viewWidth * zoomScale);
        smail.setDuration(100);
        smail.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float i = Float.parseFloat(animation.getAnimatedValue().toString());

                Log.d("123", "i = " + i);
                // 缩小放大，只需改变加减号
                nowWidth = viewWidth + i;
                nowHeight = (int) (scale * nowWidth);
                zoomAnim(viewWidth, nowWidth, viewHeight, nowHeight);
            }
        });

        smail.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (onUpdateListener != null) {
                    onUpdateListener.onStart(ZoomLayout.this);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void zoomBig() {
        big = ValueAnimator.ofFloat(nowWidth, viewWidth);
        big.setDuration(100);
        big.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float i = Float.parseFloat(animation.getAnimatedValue().toString());

                Log.d("123", "i = " + i);
                nowWidth = i;
                nowHeight = (int) (scale * nowWidth);
                zoomAnim(viewWidth, nowWidth, viewHeight, nowHeight);
            }
        });

        big.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (onUpdateListener != null) {
                    onUpdateListener.onStop(ZoomLayout.this);
                    if (isActionUp) {
                        onUpdateListener.onClick();
                    }
                    isActionUp = false;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

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

    public interface OnUpdateListener {
        void onStart(View view);

        void onStop(View view);

        void onClick();
    }

    private OnUpdateListener onUpdateListener;

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }
}
