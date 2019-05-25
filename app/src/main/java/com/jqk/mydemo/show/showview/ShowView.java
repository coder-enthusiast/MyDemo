package com.jqk.mydemo.show.showview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.chrisbanes.photoview.PhotoView;

public class ShowView extends FrameLayout {
    // 横向滑动
    private static final int H = 1;
    // 竖向滑动
    private static final int V = 2;
    // 展开动画时间，毫秒
    private static final long STARTANIMTIME = 250;

    private float moveY;
    private View contentView, bgView;
    private int screenWidth, screenHeight;

    private Activity activity;
    private float x, y, width, height;
    private ViewGroup.LayoutParams lp;
    private boolean animing = false;

    private PhotoView photoView;
    private boolean lockOrientation = false;
    // 滑动方向
    private int orientation;
    float xx = 0;
    float yy = 0;

    float xxx = 0;
    float yyy = 0;

    public ShowView(Context context) {
        super(context);
        init();
    }

    public ShowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setVisibility(View.GONE);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setContentView(View contentView) throws Exception {
        this.contentView = contentView;
        if (bgView == null) {
            throw new Exception("background no set");
        } else {
            addView(contentView);
        }
    }

    public void setBgView(View bgView) {
        this.bgView = bgView;
        addView(bgView);
    }

    /**
     * 设置当前photoView对象，用于判断滑动的临界条件
     *
     * @param photoView
     */
    public void setPhotoView(PhotoView photoView) {
        this.photoView = photoView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 判断滑动方向 横向滑动时showView不能下拉
        // 竖向滑动时dispatchTouchEvent返回true，自己消耗事件（也就是viewpager不能左右滑动）
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xx = ev.getX(0);
                yy = ev.getY(0);
                lockOrientation = false;
                break;
            case MotionEvent.ACTION_MOVE:
                xxx = ev.getX(0);
                yyy = ev.getY(0);


                if (Math.abs(xxx - xx) > 20 || Math.abs(yyy - yy) > 20) { // 增加判断方向的准确性
                    if (Math.abs(xxx - xx) > Math.abs(yyy - yy) && !lockOrientation) {
                        lockOrientation = true;
                        orientation = H;
//                    Log.d("123", "横滑");
                    } else if (Math.abs(xxx - xx) < Math.abs(yyy - yy) && !lockOrientation) {
                        lockOrientation = true;
                        orientation = V;
//                    Log.d("123", "竖滑");
                    }

                    // 有两个焦点并且竖向滑动时处理触摸事件，photoView缩放为1时处理滚动并消费事件
                    // photoView缩放不为1时复位contentView
                    if (ev.getPointerCount() == 1) {
                        if (orientation == V) {
                            if (photoView.getScale() == 1) {
                                // 计算移动距离
                                moveY = yyy - yy;
                                // 设置contentView Y轴坐标和背景透明度
                                contentView.setY(moveY);
                                float alpha = 1.0f - Math.abs(moveY / screenHeight);
                                bgView.setAlpha(alpha);
                                return true;
                            } else {
                                moveY = 0;
                                contentView.setY(moveY);
                            }
                        }
                    }

                }

                break;
            case MotionEvent.ACTION_UP:
                // 手指抬起时，如果滑动则执行隐藏动画
                if (Math.abs(moveY) > 0) {
                    hideAnim();
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    public void hideAnim() {
        ValueAnimator valueAnimator;
        if (moveY >= 0) {
            valueAnimator = ValueAnimator.ofFloat(moveY, screenHeight);
        } else {
            valueAnimator = ValueAnimator.ofFloat(moveY, -screenHeight);
        }
        valueAnimator.setDuration((long) ((Math.abs(screenHeight) - Math.abs(moveY)) / 6));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                contentView.setY(value);
                float alpha = 1.0f - Math.abs(value) / screenHeight;
                bgView.setAlpha(alpha);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animing = false;
                moveY = 0;
                contentView.setY(moveY);
                setVisibility(View.INVISIBLE);
                bgView.setAlpha(1.0f);
                View decorView = activity.getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(uiOptions);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        if (!animing) {
            valueAnimator.start();
        }
    }

    public void showAnim(float startX, float endX, float startY, float endY, float startWidth, float startHeight) {
        ValueAnimator animX = ValueAnimator.ofFloat(startX, endX);
        animX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                x = (float) animation.getAnimatedValue();
            }
        });
        ValueAnimator animY = ValueAnimator.ofFloat(startY, endY);
        animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                y = (float) animation.getAnimatedValue();
            }
        });
        ValueAnimator animWidth = ValueAnimator.ofFloat(startWidth, screenWidth);
        animWidth.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                width = (float) animation.getAnimatedValue();
            }
        });
        ValueAnimator animHeight = ValueAnimator.ofFloat(startHeight, screenHeight);
        animHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                height = (float) animation.getAnimatedValue();

                lp.width = (int) width;
                lp.height = (int) height;
                contentView.setLayoutParams(lp);

                contentView.setX(x);
                contentView.setY(y);
            }
        });
        ValueAnimator alphaAnim = ValueAnimator.ofFloat(0, 1.f);
        alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bgView.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animX, animY, animWidth, animHeight, alphaAnim);
        set.setDuration(STARTANIMTIME);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animing = true;
                setVisibility(View.VISIBLE);
                View decorView = activity.getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(uiOptions);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animing = false;
                lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        if (!animing) {
            set.start();
        }

    }
}
