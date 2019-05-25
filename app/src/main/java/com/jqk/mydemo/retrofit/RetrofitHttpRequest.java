package com.jqk.mydemo.retrofit;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2018/5/20.
 */

public class RetrofitHttpRequest {
    // 超时时间
    private static final long DEFAULT_TIMEOUT = 30;
    private volatile static RetrofitHttpRequest mInstance;
    public Retrofit mRetrofit;
    public RetrofitService retrofitService;

    private RetrofitHttpRequest() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/toutiao/")
                .client(genericClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitService = mRetrofit.create(RetrofitService.class);
    }

    /**
     * 仿照官方API的写法
     *
     * @return RetrofitHttpRequest实例
     */
    public static RetrofitHttpRequest getInstance() {
        if (mInstance != null) {
            return mInstance;
        }
        synchronized (RetrofitHttpRequest.class) {
            if (mInstance == null)
                mInstance = new RetrofitHttpRequest();
        }
        return mInstance;
    }

    public static OkHttpClient genericClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return httpClient;
    }
}
