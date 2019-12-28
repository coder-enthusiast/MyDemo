package com.jqk.mydemo.mvvmdagger

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRetroitService {
    @GET("index")
    fun getNews(@Query("type") type: String,
                @Query("key") key: String): Observable<News>
}