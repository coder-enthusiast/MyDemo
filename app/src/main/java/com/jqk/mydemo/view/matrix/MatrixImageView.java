package com.jqk.mydemo.view.matrix;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

public class MatrixImageView extends AppCompatImageView {

    private MatrixAttacher attacher;

    public MatrixImageView(Context context) {
        super(context);
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        attacher = new MatrixAttacher(this);
        super.setScaleType(ScaleType.MATRIX);
    }
}
