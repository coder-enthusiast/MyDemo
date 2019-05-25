package com.jqk.mydemo.view.matrix;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

public class MatrixAttacher implements View.OnTouchListener,
        View.OnLayoutChangeListener {

    private ImageView mImageView;

    private final Matrix mBaseMatrix = new Matrix();
    private final Matrix mDrawMatrix = new Matrix();
    private final Matrix mSuppMatrix = new Matrix();
    private final RectF mDisplayRect = new RectF();
    private final float[] mMatrixValues = new float[9];

    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;
    private float mBaseRotation;

    private CustomGestureDetector mScaleDragDetector;

    private ScaleGestureDetector scaleGestureDetector;

    private OnGestureListener onGestureListener = new OnGestureListener() {
        @Override
        public void onDrag(float dx, float dy) {
            if (mScaleDragDetector.isScaling()) {
                return; // Do not drag if we are already scaling
            }

            /*
             * Here we decide whether to let the ImageView's parent to start taking
             * over the touch event.
             *
             * First we check whether this function is enabled. We never want the
             * parent to take over if we're scaling. We then check the edge we're
             * on, and the direction of the scroll (i.e. if we're pulling against
             * the edge, aka 'overscrolling', let the parent take over).
             */
        }

        @Override
        public void onFling(float startX, float startY, float velocityX, float velocityY) {
        }

        @Override
        public void onScale(float scaleFactor, float focusX, float focusY) {
            Log.d("123", "自定义 = " + scaleFactor);
            mSuppMatrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
            checkAndDisplayMatrix();
        }
    };

    public MatrixAttacher(ImageView mImageView) {
        this.mImageView = mImageView;
        mScaleDragDetector = new CustomGestureDetector(mImageView.getContext(), onGestureListener);

        ScaleGestureDetector.OnScaleGestureListener mScaleListener = new ScaleGestureDetector.OnScaleGestureListener() {

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();

                if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor))
                    return false;
                Log.d("123", "系统 = " + scaleFactor);
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                // NO-OP
            }
        };
        scaleGestureDetector = new ScaleGestureDetector(mImageView.getContext(), mScaleListener);

        mImageView.setOnTouchListener(this);
        mImageView.addOnLayoutChangeListener(this);

        mBaseRotation = 0.0f;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        mScaleDragDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (left != oldLeft || top != oldTop || right != oldRight || bottom != oldBottom) {
            updateBaseMatrix(mImageView.getDrawable());
        }
    }

    private void updateBaseMatrix(Drawable drawable) {
        if (drawable == null) {
            return;
        }

        final float viewWidth = getImageViewWidth(mImageView);
        final float viewHeight = getImageViewHeight(mImageView);
        final int drawableWidth = drawable.getIntrinsicWidth();
        final int drawableHeight = drawable.getIntrinsicHeight();

        mBaseMatrix.reset();

        final float widthScale = viewWidth / drawableWidth;
        final float heightScale = viewHeight / drawableHeight;

        if (mScaleType == ImageView.ScaleType.CENTER) {
            mBaseMatrix.postTranslate((viewWidth - drawableWidth) / 2F,
                    (viewHeight - drawableHeight) / 2F);

        } else if (mScaleType == ImageView.ScaleType.CENTER_CROP) {
            float scale = Math.max(widthScale, heightScale);
            mBaseMatrix.postScale(scale, scale);
            mBaseMatrix.postTranslate((viewWidth - drawableWidth * scale) / 2F,
                    (viewHeight - drawableHeight * scale) / 2F);

        } else if (mScaleType == ImageView.ScaleType.CENTER_INSIDE) {
            float scale = Math.min(1.0f, Math.min(widthScale, heightScale));
            mBaseMatrix.postScale(scale, scale);
            mBaseMatrix.postTranslate((viewWidth - drawableWidth * scale) / 2F,
                    (viewHeight - drawableHeight * scale) / 2F);

        } else {
            RectF mTempSrc = new RectF(0, 0, drawableWidth, drawableHeight);
            RectF mTempDst = new RectF(0, 0, viewWidth, viewHeight);

            if ((int) mBaseRotation % 180 != 0) {
                mTempSrc = new RectF(0, 0, drawableHeight, drawableWidth);
            }

            switch (mScaleType) {
                case FIT_CENTER:
                    mBaseMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.CENTER);
                    break;

                case FIT_START:
                    mBaseMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.START);
                    break;

                case FIT_END:
                    mBaseMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.END);
                    break;

                case FIT_XY:
                    mBaseMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.FILL);
                    break;

                default:
                    break;
            }
        }

        resetMatrix();
    }

    private int getImageViewWidth(ImageView imageView) {
        return imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
    }

    private int getImageViewHeight(ImageView imageView) {
        return imageView.getHeight() - imageView.getPaddingTop() - imageView.getPaddingBottom();
    }

    private void resetMatrix() {
        mSuppMatrix.reset();
        setImageViewMatrix(getDrawMatrix());
    }


    private Matrix getDrawMatrix() {
        mDrawMatrix.set(mBaseMatrix);
        mDrawMatrix.postConcat(mSuppMatrix);
        return mDrawMatrix;
    }

    private void setImageViewMatrix(Matrix matrix) {
        mImageView.setImageMatrix(matrix);
    }

    private void checkAndDisplayMatrix() {
        setImageViewMatrix(getDrawMatrix());
    }

    class MyScaleGestureDetector implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();

            if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor))
                return false;
            Log.d("123", "系统 = " + scaleFactor);
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
