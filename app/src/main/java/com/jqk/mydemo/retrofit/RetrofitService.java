package com.jqk.mydemo.retrofit;

import com.jqk.mydemo.mvp.Login;
import com.jqk.mydemo.mvvm.Weather;
import com.jqk.mydemo.mvvmnew.news.News;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/20.
 */

public interface RetrofitService {
    @GET("77868677")
    Observable<String> networkRequest();

    @GET("101190408.html")
    Observable<Login> login(@Query("userName") String userName,
                            @Query("passWord") String passWord);

    @GET("101190407.html")
    Observable<Weather> getWeather();

    @GET("101190408.html")
    Observable<Weather> getWeather1();

    @GET("index")
    Observable<News> getNews(@Query("type") String type,
                             @Query("key") String key);


    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("business/user/loginByMobile/name")
    Observable<Login> login(@Body RequestBody requestBody);
    //    val body = RequestBody.create(MediaType.parse("application/json"), Gson().toJson(postData))
    // postData是一个Map
}
