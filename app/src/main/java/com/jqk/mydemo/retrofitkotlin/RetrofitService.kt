package com.jqk.mydemo.retrofitkotlin

import com.jqk.mydemo.mvvmnew.news.News
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("index")
    fun getNews(@Query("type") type: String,
                @Query("key") key: String): Observable<News>

    @GET("index")
    fun getNew(@Query("type") type: String,
               @Query("key") key: String): Call<News>


}