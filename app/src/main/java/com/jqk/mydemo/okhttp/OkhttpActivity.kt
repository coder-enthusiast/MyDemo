package com.jqk.mydemo.okhttp

import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityOkhttpBinding
import okhttp3.*
import java.io.IOException
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.FormBody
import okhttp3.RequestBody
import android.os.Environment.getExternalStorageDirectory
import java.io.File


class OkhttpActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName

    lateinit var b: ActivityOkhttpBinding
    val url = "https://www.baidu.com/"
    val baseUrl = "http://v.juhe.cn/toutiao/index"

    var responseBody: ObservableField<String> = ObservableField()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_okhttp)
        b.view = this
        get()
//        post()
//        downloadFile()
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

    fun get() {
        //1.创建OkHttpClient对象
        val okHttpClient = buildOkHttpClient()
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        val request = Request.Builder().url(url).method("GET", null).build()
        //3.创建一个call对象,参数就是Request请求对象
        val call = okHttpClient.newCall(request)
        //4.请求加入调度，重写回调方法
        call.enqueue(object : Callback {
            //请求失败执行的方法
            override fun onFailure(call: Call, e: IOException) {

            }

            //请求成功执行的方法
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "call = " + call.toString())
                Log.d(TAG, "response.body() = " + response.body())
                responseBody.set(response.body()!!.string())
            }
        })
    }

    fun post() {
        //1.创建OkHttpClient对象
        val okHttpClient = buildOkHttpClient()
        //2.通过new FormBody()调用build方法,创建一个RequestBody,可以用add添加键值对
        val requestBody = FormBody.Builder()
                .add("type", "guonei")
                .add("key", "93ff5c6fd6dc134fc69f6ffe3bc568a6")
                .build()
        //3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
        val request = Request.Builder().url(baseUrl).post(requestBody).build()
        //4.创建一个call对象,参数就是Request请求对象
        val call = okHttpClient.newCall(request)
        //5.请求加入调度,重写回调方法
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                responseBody.set(response.body()!!.string())
            }
        })
    }

    // 暂无验证
    fun postString() {
        val mediaType = MediaType.parse("application/json; charset=utf-8")//"类型,字节码"
        //字符串
        val value = "{username:admin;password:admin}"
        //1.创建OkHttpClient对象
        val okHttpClient = OkHttpClient()
        //2.通过RequestBody.create 创建requestBody对象
        val requestBody = RequestBody.create(mediaType, value)
        //3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
        val request = Request.Builder().url("url").post(requestBody).build()
        //4.创建一个call对象,参数就是Request请求对象
        val call = okHttpClient.newCall(request)
        //5.请求加入调度,重写回调方法
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
            }
        })
    }

    // 暂无验证
    fun uploadFile() {
        //1.创建OkHttpClient对象
        val okHttpClient = OkHttpClient()
        //上传的图片
        val file = File(getExternalStorageDirectory(), "zhuangqilu.png")
        //2.通过RequestBody.create 创建requestBody对象,application/octet-stream 表示文件是任意二进制数据流
        val requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)
        //3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
        val request = Request.Builder().url("url").post(requestBody).build()
        //4.创建一个call对象,参数就是Request请求对象
        val call = okHttpClient.newCall(request)
        //5.请求加入调度,重写回调方法
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
            }
        })
    }

    fun downloadFile() {
        //1.创建OkHttpClient对象
        val okHttpClient = OkHttpClient()
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        val request = Request.Builder().url("https://www.baidu.com/img/bd_logo1.png").get().build()
        //3.创建一个call对象,参数就是Request请求对象
        val call = okHttpClient.newCall(request)
        //4.请求加入调度,重写回调方法
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "onFailure: " + call.toString())
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //拿到字节流
//                var inputSteam = response.body()?.byteStream()
//                var len: Int? = 0
//                //设置下载图片存储路径和名称
//                val file = File(Environment.getExternalStorageDirectory(), "baidu.png")
//                val fos = FileOutputStream(file)
//                val buf = ByteArray(128)
//
//                do {
//                    len = inputSteam?.read(buf) ?: -1
//                    if (len != -1) {
//                        fos.write(buf, 0, len)
//                        Log.d(TAG, "onResponse: $len")
//                    }
//                } while (len != -1)
//                fos.flush()
//                fos.close()
//                inputSteam?.close()

                val inputStream = response.body()?.byteStream()
                //使用 BitmapFactory 的 decodeStream 将图片的输入流直接转换为 Bitmap
                val bitmap = BitmapFactory.decodeStream(inputStream)
                //在主线程中操作UI
                runOnUiThread {
                    //然后将Bitmap设置到 ImageView 中
                    b.img.setImageBitmap(bitmap)
                }

                inputStream?.close()
            }
        })
    }

    // 暂无验证
    private fun sendMultipart() {
        val okHttpClient = OkHttpClient()
        val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "wangshu")
                .addFormDataPart("image", "wangshu.jpg",
                        RequestBody.create(MediaType.parse("application/octet-stream"), File("/sdcard/wangshu.jpg")))
                .build()
        val request = Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.i(TAG, response.body()?.string())
            }
        })
    }
}