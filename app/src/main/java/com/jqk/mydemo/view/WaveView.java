package com.jqk.mydemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 预期效果：圆形波浪，高度可以调节
 * 正选曲线公式：y=Asin(ωx+φ)+k
 * 周期 = 2π/ω
 * 振幅 = A
 * 偏移量 = φ
 * Y轴位移 = k
 */
public class WaveView extends View {
    // view的宽高
    private int viewWidth, viewHeight;
    // 波浪画笔
    private Paint wavePaint;
    // 圆半径
    private float radius;
    // 波浪颜色
    private String waveColor = "#8B5742";

    private float pianyiliang = 0;
    float zhenfu = 20;
    float jiaosudu;
    float zhouqi;
    float percentage = 50;
    float line;
    Path path;

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);
        wavePaint.setStyle(Paint.Style.FILL);
        wavePaint.setColor(Color.parseColor(waveColor));

        path = new Path();
    }

    public void setPercentage(float percentage) {
        if (percentage <= 0) {
            this.percentage = 0;
            zhenfu = 0;
        } else if (percentage >= 100) {
            this.percentage = 100;
            zhenfu = 0;
        } else {
            this.percentage = percentage;
        }

        if (percentage <= 40 && percentage > 0) {
            zhenfu = percentage / 2.0f;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthSpecMode) {
            case MeasureSpec.UNSPECIFIED:// 未确认 测量规范模式：父项没有对子项施加任何约束。 它可以是任何它想要的大小。
                viewWidth = 20;
                break;
            case MeasureSpec.AT_MOST: // 究竟 测量规格模式：父母已确定孩子的确切大小。 孩子将被给予那些边界，无论它想要多大。
            case MeasureSpec.EXACTLY: // 最多 测量规格模式：孩子可以大到它想要达到指定的尺寸。
                viewWidth = widthSpecSize;
                break;
        }
        switch (heightSpecMode) {
            case MeasureSpec.UNSPECIFIED:
                viewHeight = 20;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                viewHeight = heightSpecSize;
                break;
        }

        if (viewWidth < viewHeight) {
            viewHeight = viewWidth;
            radius = viewHeight / 2.0f;
        } else {
            viewWidth = viewHeight;
            radius = viewWidth / 2.0f;
        }
        // 默认在中线
        line = viewHeight * (100 - percentage) / 100;
        Log.d("123", "line = " + line);

        // 控件宽度的1/3为一个周期
        zhouqi = viewWidth / 3;
        jiaosudu = (float) (Math.PI * 2 / zhouqi);
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        anim();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        line = viewHeight * (100 - percentage) / 100;

        path.reset();

        canvas.saveLayerAlpha(0, 0, getWidth(), getHeight(), 255,
                Canvas.ALL_SAVE_FLAG);

        canvas.drawCircle(viewWidth / 2, viewHeight / 2, radius, wavePaint);

        wavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        for (int i = 0; i <= viewWidth; i++) {
            path.lineTo((float) i,
                    (float) (zhenfu * (Math.sin(jiaosudu * i - pianyiliang)) + line));
        }
        path.lineTo(viewWidth, viewHeight);
        path.lineTo(0, viewHeight);
        canvas.drawPath(path, wavePaint);

        wavePaint.setXfermode(null);
    }

    public void anim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 2f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pianyiliang = (float) ((float) animation.getAnimatedValue() * Math.PI);
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        // 1秒移动一个周期
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }
}
