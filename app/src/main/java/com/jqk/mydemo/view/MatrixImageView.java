package com.jqk.mydemo.view;

import android.content.Context;
import android.graphics.Matrix;
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

public class MatrixImageView extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener {

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

    private boolean scaling = false;

    public MatrixImageView(Context context) {
        super(context);
        init();
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MatrixImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onGlobalLayout() {
        Log.d("123", "onGlobalLayout");
        float scale;

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

        imgWidth = getMeasuredWidth();
        imgHeight = getMeasuredHeight();

        bitmapWidth = getDrawable().getBounds().width();
        bitmapHeight = getDrawable().getBounds().height();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mGestureDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
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
    }

    public void initScaleGestureListener() {
        onScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                scaling = true;
                if (getDrawable() == null)
                    return true;
                checkMatrix(detector);
                mSuppMatrix.postScale(imgScale, imgScale, centerX, centerY);
                setImageViewMatrix(getDrawMatrix());
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                scaling = true;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                scaling = false;
            }
        };
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (!scaling) {
                mSuppMatrix.postTranslate(-distanceX, -distanceY);
//                checkBorderAndCenterWhenScale();
                setImageViewMatrix(getDrawMatrix());
                scaling = false;
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    public void checkMatrix(ScaleGestureDetector detector) {
//        if (getScale() * detector.getScaleFactor() <= minScale * defalutScale) {
//            if (detector.getScaleFactor() <= 1.0f) {
//                imgScale = defalutScale;
//            } else {
//                imgScale = detector.getScaleFactor();
//            }
//
//        } else if (getScale() * detector.getScaleFactor() > minScale * defalutScale && getScale() * detector.getScaleFactor() < maxScale * defalutScale) {
//            imgScale = detector.getScaleFactor() ;
//        } else if (getScale() * detector.getScaleFactor() >= maxScale * defalutScale) {
//            if (detector.getScaleFactor() <= 1.0f) {
//                imgScale = detector.getScaleFactor() ;
//            } else {
//                imgScale = defalutScale;
//            }
//        }
        imgScale = detector.getScaleFactor();
        centerX = detector.getFocusX();
        centerY = detector.getFocusY();
    }

    public float getScale() {
        return (float) Math.sqrt((float) Math.pow(getValue(mSuppMatrix, Matrix.MSCALE_X), 2) + (float) Math.pow(getValue(mSuppMatrix, Matrix.MSKEW_Y), 2));
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

    private void checkBorderAndCenterWhenScale() {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height) {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }

        mSuppMatrix.postTranslate(deltaX, deltaY);

    }


    private RectF getMatrixRectF() {
        Matrix matrix = mSuppMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }
}
