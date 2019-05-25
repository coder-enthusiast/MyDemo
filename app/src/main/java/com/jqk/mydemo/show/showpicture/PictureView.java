package com.jqk.mydemo.show.showpicture;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.jqk.mydemo.util.Constants;

/**
 * 通过属性动画移动整个布局实现，实现图片的浏览效果
 */
public class PictureView extends FrameLayout {
    // 横向滑动
    private static final int H = 1;
    // 竖向滑动
    private static final int V = 2;
    // 展开动画时间，毫秒
    private static final long STARTANIMTIME = 220;

    private float moveY;
    private View contentView, bgView;
    private int screenWidth, screenHeight;

    private Activity activity;
    private float x, y, width, height;
    private ViewGroup.LayoutParams lp;
    private boolean animing = false;

    private int type;
    private PageView pageView;
    private boolean lockOrientation = false;
    // 滑动方向
    private int orientation;
    float xx = 0;
    float yy = 0;

    float xxx = 0;
    float yyy = 0;

    private ViewType viewType;
    private boolean canHide = false;
    private boolean show = false;

    public PictureView(Context context) {
        super(context);
        init();
    }

    public PictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PictureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
     * 设置当前pageView对象，用于判断滑动的临界条件等操作
     *
     * @param
     */
    public void setPageView(PageView pageView, ViewType viewType) {
        this.pageView = pageView;
        this.type = viewType.getType();
        this.viewType = viewType;
    }

    public void onPageSelected(PageView pageView, ViewType viewType) {
        setPageView(pageView, viewType);
        if (!show) {
            loadResAnimEnd(viewType);
        }
    }

    public void loadResAnimEnd(final ViewType viewType) {
        Log.d("123", "loadResAnimEnd");
        switch (viewType.getType()) {
            case Constants.TYPE_NORMALPICTURE:
                pageView.hideNormalImg(false);
                break;
            case Constants.TYPE_LONGPICTURE:
                pageView.hideLongImg(false);
                break;
            case Constants.TYPE_VIDEO:

                break;
        }
    }

    public void show(float startX, float endX, float startY, float endY, float startWidth, float startHeight) {
//        Log.d("123", "viewType = " + viewType.toString());
        showAnim(startX, endX, startY, endY, startWidth, startHeight);
        show = true;
    }

    public void hide() {
        hideAnim();
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
                canHide = false;
                pageView.setCanScroll(true);
                break;
            case MotionEvent.ACTION_MOVE:
                switch (type) {
                    case Constants.TYPE_NORMALPICTURE:
                        xxx = ev.getX(0);
                        yyy = ev.getY(0);

                        if (Math.abs(xxx - xx) > 2 || Math.abs(yyy - yy) > 2) { // 增加判断方向的准确性
                            if (Math.abs(xxx - xx) > Math.abs(yyy - yy) && !lockOrientation) {
                                lockOrientation = true;
                                orientation = H;
                            } else if (Math.abs(xxx - xx) < Math.abs(yyy - yy) && !lockOrientation) {
                                lockOrientation = true;
                                orientation = V;
                            }

                            // 有一个焦点并且竖向滑动时处理触摸事件，photoView缩放为1时处理滚动并消费事件
                            if (ev.getPointerCount() == 1) {
                                if (orientation == V) {
                                    // photoView缩放不为1时复位contentView
                                    if ((pageView.getNormalImg()).getScale() == 1) {
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
                    case Constants.TYPE_LONGPICTURE:
                        xxx = ev.getX(0);
                        yyy = ev.getY(0);

                        if (Math.abs(xxx - xx) > 2 || Math.abs(yyy - yy) > 2) { // 增加判断方向的准确性
                            if (Math.abs(xxx - xx) > Math.abs(yyy - yy) && !lockOrientation) {
                                lockOrientation = true;
                                orientation = H;
                            } else if (Math.abs(xxx - xx) < Math.abs(yyy - yy) && !lockOrientation) {
                                lockOrientation = true;
                                orientation = V;
                            }

                            // 有两个焦点并且竖向滑动时处理触摸事件，长图片为最小缩放值时处理滚动并消费事件
                            if (ev.getPointerCount() == 1) {
//                                Log.d("123", "isTop = " + pageView.isTop());
//                                Log.d("123", "isBottom = " + pageView.isBottom());
                                if (orientation == V) {
                                    if ((pageView.getLongImg()).isMinimum()) {
                                        // 计算移动距离
                                        moveY = yyy - yy;
                                        // 设置contentView Y轴坐标和背景透明度
//                                        Log.d("123", "moveY = " + moveY);
                                        // 当长图片不允许滑动的时候才移动整个布局
                                        if (!pageView.isScrolling()) {
                                            // 长图片能够滑动则复位contentView否则移动整个布局
                                            if ((moveY > 0 && pageView.isTop()) || (moveY < 0 && pageView.isBottom())) {
                                                contentView.setY(moveY);
                                                float alpha = 1.0f - Math.abs(moveY / screenHeight);
                                                bgView.setAlpha(alpha);
                                                pageView.setCanScroll(false);
                                                canHide = true;

                                                return true;
                                            } else {
                                                moveY = 0;
                                                contentView.setY(moveY);
                                            }
                                        } else {
                                            // 重置yy，防止布局跳动
                                            yy = ev.getY(0);
                                            moveY = 0;
                                            contentView.setY(moveY);
                                        }
                                    } else {
                                        moveY = 0;
                                        contentView.setY(moveY);
                                    }
                                } else {
                                    moveY = 0;
                                    contentView.setY(moveY);
                                }
                            }
                        }
                        break;
                    case Constants.TYPE_VIDEO:

                        break;
                }

                break;
            case MotionEvent.ACTION_UP:
                // 手指抬起时，如果滑动则执行隐藏动画
                switch (type) {
                    case Constants.TYPE_NORMALPICTURE:
                        if (Math.abs(moveY) > 0) {
                            if (Math.abs(moveY) < screenHeight / 4) {
                                resetAnim(moveY);
                            } else {
                                hideAnim();
                            }
                        }
                        break;
                    case Constants.TYPE_LONGPICTURE:
                        if (Math.abs(moveY) > 0 && canHide) {
                            if (Math.abs(moveY) < screenHeight / 4) {
                                resetAnim(moveY);
                            } else {
                                hideAnim();
                            }
                        }
                        break;
                    case Constants.TYPE_VIDEO:

                        break;
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    public void hideAnim() {
        ValueAnimator valueAnimator;
        if (moveY > 0) {
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
                // 隐藏所有的img
                onHideImgListener.hideImg();
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
        set.setInterpolator(new LinearInterpolator());
        set.playTogether(animX, animY, animWidth, animHeight, alphaAnim);
        set.setDuration(STARTANIMTIME);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animing = true;
                setVisibility(View.VISIBLE);
                View decorView = activity.getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(uiOptions);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animing = false;
                lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                loadResAnimEnd(viewType);
                show = false;
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

    public void resetAnim(float y) {
        ValueAnimator animY = ValueAnimator.ofFloat(y, 0);
        animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                contentView.setY((float) animation.getAnimatedValue());
            }
        });
        animY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                bgView.setAlpha(1.0f);
                moveY = 0.0f;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animY.setDuration(0);
        animY.start();
    }

    public interface OnHideImgListener {
        void hideImg();
    }

    private OnHideImgListener onHideImgListener;

    public void setOnHideImgListener(OnHideImgListener onHideImgListener) {
        this.onHideImgListener = onHideImgListener;
    }
}
