package com.jqk.mydemo.coroutines

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jqk.mydemo.R
import com.jqk.commonlibrary.util.L
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

class NetWorkActivity : AppCompatActivity() {
    val url = "https://www.baidu.com/"

    lateinit var start: Button
    lateinit var content: TextView


    var job: Job? = null
    var job2: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_network)
        content = findViewById(R.id.content)
        start = findViewById(R.id.start)
        start.setOnClickListener {

//            lifecycleScope跟LifecycleOwner绑定,LifecycleOwner被销毁时协程取消
            job = lifecycleScope.launch(Dispatchers.IO) {
                //                val time = measureTimeMillis {
//                    val one = async { doSomethingUsefulOne() }
//                    val two = async { doSomethingUsefulTwo() }
//                    L.d("The answer is ${one.await() + two.await()}")
//                }
//                L.d("Completed in $time ms")

//                val data = networkRequest()
//                withContext(Dispatchers.Main) {
//                    delay(5000L)
//
//                    L.d("设置界面")
//                    content.text = data
//                }

                networkRequestFlow().collect { value ->
                    withContext(Dispatchers.Main) {
                        delay(5000L)
                        L.d("设置界面")
                        if (isActive) {
                            content.text = value
                        }
                    }
                }
            }
        }
    }

    fun networkRequestFlow(): Flow<String> = flow {
        val okHttpClient = buildOkHttpClient()
        val request = Request.Builder().url(url).method("GET", null).build()

        var data = ""

        try {
            data = okHttpClient.newCall(request).execute().body()!!.string()
        } catch (e: Exception) {
            data = e.toString()
        }
        emit(data)
    }

    suspend fun networkRequest(): String {
        val okHttpClient = buildOkHttpClient()
        val request = Request.Builder().url(url).method("GET", null).build()

        var data = ""

        try {
            data = okHttpClient.newCall(request).execute().body()!!.string()
        } catch (e: Exception) {
            data = e.toString()
        }

        return data
    }

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // 假设我们在这里做了些有用的事
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // 假设我们在这里也做了些有用的事
        return 29
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

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}