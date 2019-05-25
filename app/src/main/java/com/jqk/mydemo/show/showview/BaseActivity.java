package com.jqk.mydemo.show.showview;

import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;

public class BaseActivity extends AppCompatActivity {
    public ShowView showView;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (showView != null && showView.isShown()) {
                showView.hideAnim();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
