package com.jqk.mydemo.webview;

import androidx.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jqk.mydemo.R;
import com.jqk.mydemo.databinding.ActivityWebviewBinding;

public class WebViewActivityForJava extends AppCompatActivity {
    private ActivityWebviewBinding binding;

    private int screenWidth, screenHeight;
    private String latestUrl, prevUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview);

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        screenHeight = screenHeight - result;

        ViewGroup.LayoutParams lp = binding.webView.getLayoutParams();
        lp.width = screenWidth;
        lp.height = screenHeight;
        binding.webView.setLayoutParams(lp);
        prevUrl = "https://www.amazon.co.jp/dp/B07H9QDNHR";

        binding.webView.loadUrl(prevUrl);

        binding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.d("ddd", "errorCode = " + errorCode);
                if (errorCode == 404) {
                    binding.webView.loadUrl("file:///android_asset/web/NotFindPage.html");
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    int errorCode = error.getErrorCode();
                    Log.d("ddd", "errorCode = " + errorCode);
                    if (errorCode == 404) {
                        binding.webView.loadUrl("file:///android_asset/web/NotFindPage.html");
                    }
                }
            }
        });
    }
}
