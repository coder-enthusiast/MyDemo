package com.jqk.mydemo.mvvmdagger

import com.jqk.commonlibrary.util.L
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofit(): NewsRetroitService {
        L.d("NewsRetroitService")
        return RetrofitHttpRequest().retrofitService
    }
}