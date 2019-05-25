package com.jqk.mydemo.show.showpicture;

import android.content.Context;
import android.graphics.PointF;
import android.net.Uri;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.PhotoView;
import com.jqk.mydemo.R;
import com.jqk.mydemo.glide.GlideApp;
import com.jqk.mydemo.util.Constants;

import java.io.File;

import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.SCALE_TYPE_CUSTOM;

public class PageView extends LinearLayout {
    private PhotoView normalImg, normalImgPlaceholder, longImgPlaceholder;
    private SubsamplingScaleImageView longImg;
    private FrameLayout normalImageView, longImageView;

    public PageView(Context context) {
        super(context);
        init();
    }

    public PageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_viewpager, this, false);
        normalImg = view.findViewById(R.id.normalImg);
        longImg = view.findViewById(R.id.longImg);
        normalImgPlaceholder = view.findViewById(R.id.normalImgPlaceholder);
        normalImageView = view.findViewById(R.id.normalImageView);
        longImageView = view.findViewById(R.id.longImageView);
        longImgPlaceholder = view.findViewById(R.id.longImgPlaceholder);

        addView(view);
    }

    public boolean isTop() {
//        Log.d("123", "isTop = " + longImg.isTop());
        return longImg.isTop();
    }

    public boolean isBottom() {
//        Log.d("123", "isBottom = " + longImg.isBottom());
        return longImg.isBottom();
    }

    public boolean isScrolling() {
//        Log.d("123", "isPanning = " + longImg.isPanning());
        return longImg.isPanning();
    }

    public void setCanScroll(boolean canScroll) {
        longImg.setCanScroll(canScroll);
    }

    public void loadPlacehoderResourse(final ViewType viewType) {
        showView(viewType.getType());
        switch (viewType.getType()) {
            case Constants.TYPE_NORMALPICTURE:
                normalImgPlaceholder.setImageBitmap(viewType.getBitmap());
                GlideApp.with(getContext())
                        .load(viewType.getPath())
                        .into(normalImg);
                break;
            case Constants.TYPE_LONGPICTURE:
                longImgPlaceholder.setImageBitmap(viewType.getBitmap());
                GlideApp.with(getContext())
                        .load(viewType.getPath())
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, Transition<? super File> transition) {
                                Log.d("123", "load");
                                getLongImg().setMinimumScaleType(SCALE_TYPE_CUSTOM);
                                getLongImg().setMinScale(1.5f);
                                getLongImg().setImage(ImageSource.uri(Uri.fromFile(resource)));
                            }
                        });
                break;
            case Constants.TYPE_VIDEO:

                break;
            case Constants.TYPE_ERROR:
                normalImgPlaceholder.setImageBitmap(viewType.getBitmap());
                break;
        }
    }

    public PhotoView getNormalImg() {
        return normalImg;
    }

    public void setNormalImg(PhotoView normalImg) {
        this.normalImg = normalImg;
    }

    public SubsamplingScaleImageView getLongImg() {
        return longImg;
    }

    public void setLongImg(SubsamplingScaleImageView longImg) {
        this.longImg = longImg;
    }

    public PhotoView getLongImgPlaceholder() {
        return longImgPlaceholder;
    }

    public void setLongImgPlaceholder(PhotoView longImgPlaceholder) {
        this.longImgPlaceholder = longImgPlaceholder;
    }

    public void showView(int type) {
        switch (type) {
            case Constants.TYPE_NORMALPICTURE:
                normalImageView.setVisibility(View.VISIBLE);
                longImageView.setVisibility(View.GONE);
                break;
            case Constants.TYPE_LONGPICTURE:
                longImageView.setVisibility(View.VISIBLE);
                normalImageView.setVisibility(View.GONE);
                break;
            case Constants.TYPE_VIDEO:

                break;
        }
    }

    public void hideLongImg(boolean isPlaceholder) {
//        Log.d("123", "hideLongImg = " + isPlaceholder);
        if (isPlaceholder) {
            longImg.setVisibility(View.INVISIBLE);
        } else {
            longImg.setScaleAndCenter(longImg.getMinScale(), new PointF());
            longImg.setVisibility(View.VISIBLE);
        }

    }

    public void hideNormalImg(boolean isPlaceholder) {
//        Log.d("123", "hideNormalImg = " + isPlaceholder);
        if (isPlaceholder) {
            normalImg.setVisibility(View.INVISIBLE);
        } else {
            normalImg.setVisibility(View.VISIBLE);
        }
    }
}
