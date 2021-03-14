package com.jqk.mydemo.mvp;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jqk.commonlibrary.util.L;
import com.jqk.commonlibrary.util.ScreenUtil;
import com.jqk.mydemo.R;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class ProgressDialog extends DialogFragment {

    private LinearLayout contentView;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();//获取dialog布局的参数


        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;//全屏
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;//全屏

        getDialog().getWindow().setAttributes(layoutParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_progress, container, false);
        contentView = view.findViewById(R.id.contentView);
        progressBar = view.findViewById(R.id.progressBar);

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        float y = motionEvent.getY();
                        float height = progressBar.getHeight();

                        L.d("jqk", "y = " + y);
                        L.d("jqk", "height = " + height);

                        if (y < (ScreenUtil.getScreenHeight(getContext()) - height) / 2) {
                            dismiss();
                        }

                    }
                }

                return false;
            }
        });

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
