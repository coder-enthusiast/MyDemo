package com.jqk.mydemo.retrofitkotlin

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * kotlin单例模式（双重校验锁模式）
 */
class RetrofitHttpRequest private constructor() {
    companion object {
        val instance: RetrofitHttpRequest by lazy {
            RetrofitHttpRequest()
        }
    }

    val DEFAULT_TIMEOUT: Long = 30
    var mRetrofit: Retrofit
    var retrofitService: RetrofitService

    init {
        mRetrofit = Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/toutiao/")
                .client(genericClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        retrofitService = mRetrofit.create(RetrofitService::class.java)
    }

    fun genericClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            //打印retrofit日志
            Log.i("RetrofitLog", "retrofitBack = $message")
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build()
    }
}