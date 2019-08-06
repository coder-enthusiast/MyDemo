package com.jqk.mydemo.coroutines

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R
import com.jqk.mydemo.util.L
import kotlinx.android.synthetic.main.activity_okhttp.*
import kotlinx.coroutines.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class NetWorkActivity : AppCompatActivity() {
    val url = "https://www.baidu.com/"

    lateinit var start: Button
    lateinit var content: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_network)
        content = findViewById(R.id.content)
        start = findViewById(R.id.start)
        start.setOnClickListener {
            GlobalScope.launch(newSingleThreadContext("newThread")) {
                val okHttpClient = buildOkHttpClient()
                val request = Request.Builder().url(url).method("GET", null).build()
                val response = okHttpClient.newCall(request).execute()
                val data = response.body()!!.string()
                withContext(Dispatchers.Main) {
                    content.text = data
                }
            }
        }


    }

    fun buildOkHttpClient(): OkHttpClient {

        val cacheSize = 10 * 1024 * 1024L
        return OkHttpClient.Builder()
                .cache(Cache(externalCacheDir.absoluteFile, cacheSize))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
    }
}