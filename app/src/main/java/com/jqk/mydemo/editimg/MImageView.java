package com.jqk.mydemo.editimg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewTreeObserver;

public class MImageView extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private GestureDetector mGestureDetector;
    private ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener;
    private ScaleGestureDetector mScaleGestureDetector;
    private final Matrix mBaseMatrix = new Matrix();
    private final Matrix mDrawMatrix = new Matrix();
    private final Matrix mSuppMatrix = new Matrix();
    private final RectF mDisplayRect = new RectF();
    private final float[] mMatrixValues = new float[9];
    private int imgWidth, imgHeight;
    private int screenWidth, screenHeight;
    private int bitmapWidth, bitmapHeight;
    private float centerX, centerY;
    private float imgScale;
    private float[] values;
    private float minScale = 1.0f;
    private float maxScale = 5.0f;

    private float defalutScale = 1;

    private float dx, dy;
    private float scrollx, scrolly;
    private float lastx, lasty;
    private Paint graffitiPaint;
    private Path graffitiPath;

    private boolean scaling = false;

    public MImageView(Context context) {
        super(context);
    }

    public MImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onGlobalLayout() {
        float scale;

        imgWidth = getMeasuredWidth();
        imgHeight = getMeasuredHeight();

        bitmapWidth = getDrawable().getBounds().width();
        bitmapHeight = getDrawable().getBounds().height();

        if (bitmapWidth > bitmapHeight) {
            scale = imgWidth / (bitmapWidth * 1.0f);
        } else {
            scale = imgHeight / (bitmapHeight * 1.0f);
        }
        mBaseMatrix.reset();
        mBaseMatrix.postTranslate((imgWidth - bitmapWidth) / 2f, (imgHeight - bitmapHeight) / 2f);
        mBaseMatrix.postScale(scale, scale, imgWidth / 2f, imgHeight / 2f);
        defalutScale = scale;
        imgScale = scale;

        centerX = imgWidth / 2f;
        centerY = imgHeight / 2f;

        resetMatrix();

        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private void resetMatrix() {
        mSuppMatrix.reset();
        setImageViewMatrix(getDrawMatrix());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x;
        float y;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                graffitiPath.reset();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2) {
                    scaling = true;
                    x = (event.getX(0) + event.getX(1)) / 2;
                    y = (event.getY(0) + event.getY(1)) / 2;

                    if (lastx == 0) {
                        lastx = x;
                        lasty = y;
                        return true;
                    }
                    dx = lastx - x;
                    dy = lasty - y;
                    scrollx += dx;
                    scrolly += dy;
//                    mSuppMatrix.postTranslate(-dx, -dy);
                    scrollTo((int) scrollx, (int) scrolly);
                    lastx = x;
                    lasty = y;
                }

                if (event.getPointerCount() == 1) {
                    if (scaling) {
                        graffitiPath.moveTo(event.getX(), event.getY());
                    }
                    scaling = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                lastx = 0;
                lasty = 0;

                float scale = 1f / getScale();
                Matrix M = new Matrix();
                M.setTranslate(getScrollX(), getScrollY());
                M.postTranslate(-getMatrixRectF().left, -getMatrixRectF().top);
                M.postScale(scale, scale);
                graffitiPath.transform(M);

                invalidate();
                break;
        }

        mGestureDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        setImageViewMatrix(getDrawMatrix());

        // 检查边界

        return true;
    }

    public void init() {
        initScaleGestureListener();
        mGestureDetector = new GestureDetector(getContext(), new MyGestureDetector());
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), onScaleGestureListener);
        values = new float[9];
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        getViewTreeObserver().addOnGlobalLayoutListener(this);

        graffitiPaint = new Paint();
        graffitiPaint.setColor(Color.RED);
        graffitiPaint.setStyle(Paint.Style.STROKE);
        graffitiPaint.setStrokeWidth(10f);

        graffitiPath = new Path();
    }

    public void initScaleGestureListener() {
        onScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                Log.d("1", "双指");
                if (getDrawable() == null)
                    return true;
                checkScale(detector);
                mSuppMatrix.postScale(imgScale, imgScale, centerX, centerY);

                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
            }
        };
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            if (!scaling) {
                graffitiPath.moveTo(e.getX(), e.getY());
            }
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // 单指划线
            if (!scaling) {
                Log.d("1", "单指");
                graffitiPath.lineTo(e2.getX(), e2.getY());
//                invalidate();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    public void checkScale(ScaleGestureDetector detector) {
        imgScale = detector.getScaleFactor();
        centerX = detector.getFocusX() + getScrollX();
        centerY = detector.getFocusY() + getScrollY();
    }

//    public float getScale() {
//        return (float) Math.sqrt((float) Math.pow(getValue(mSuppMatrix, Matrix.MSCALE_X), 2) + (float) Math.pow(getValue(mSuppMatrix, Matrix.MSKEW_Y), 2));
//    }

    public float getScale() {
        Log.d("1", "scale = " + 1f * getMatrixRectF().width() / bitmapWidth / defalutScale);
        return 1f * getMatrixRectF().width() / bitmapWidth / defalutScale;
    }

    private float getValue(Matrix matrix, int whichValue) {
        matrix.getValues(values);
        return values[whichValue];
    }

    private Matrix getDrawMatrix() {
        mDrawMatrix.set(mBaseMatrix);
        mDrawMatrix.postConcat(mSuppMatrix);
        return mDrawMatrix;
    }

    private void setImageViewMatrix(Matrix matrix) {
        setImageMatrix(matrix);
    }

    private RectF getMatrixRectF() {
        Matrix matrix = mDrawMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(getMatrixRectF(), graffitiPaint);

        canvas.save();
        canvas.translate(getMatrixRectF().left, getMatrixRectF().top);
        canvas.scale(getScale(), getScale());
        canvas.drawPath(graffitiPath, graffitiPaint);
        canvas.restore();
    }
}
