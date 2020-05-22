package com.jqk.mydemo.coroutines

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R
import com.jqk.commonlibrary.util.L
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

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

            GlobalScope.launch(Dispatchers.IO) {
                val time = measureTimeMillis {
                    val one = async { doSomethingUsefulOne() }
                    val two = async { doSomethingUsefulTwo() }
                    L.d("The answer is ${one.await() + two.await()}")
                }
                L.d("Completed in $time ms")

                val okHttpClient = buildOkHttpClient()
                val request = Request.Builder().url(url).method("GET", null).build()
                val call = okHttpClient.newCall(request)

                var data = ""

                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        data = "e = " + e.toString()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        data = response.body()!!.string()
                    }
                })

                withContext(Dispatchers.Main) {
                    content.text = data
                }
            }
        }
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
}