package com.jqk.mydemo.webview

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview)

//        binding.webView.loadUrl("http://10.108.6.131:8080/appChart")
//        binding.webView.setFindListener(object : WebView.FindListener {
//            override fun onFindResultReceived(p0: Int, p1: Int, p2: Boolean) {
//                Log.d("123", "p0 = " + p0)
//                Log.d("123", "p1 = " + p1)
//                Log.d("123", "p2 = " + p2)
//            }
//        })
//        binding.webView.setWebViewClient(object : WebViewClient() {
//            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
//                Log.d("123", "request = " + request.toString())
//                Log.d("123", "errorResponse = " + errorResponse.toString())
//                super.onReceivedHttpError(view, request, errorResponse)
//            }
//        })
//        binding.webView.setFindListener(object : WebView.FindListener {
//            override fun onFindResultReceived(p0: Int, p1: Int, p2: Boolean) {
//
//            }
//        })
    }
}