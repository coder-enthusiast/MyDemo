package com.jqk.mydemo.mvvmdagger

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class NewsViewModel : ViewModel() {

    @Inject
    lateinit var newsRetroitService: NewsRetroitService

    fun getData() {
        newsRetroitService.getNews("top", "93ff5c6fd6dc134fc69f6ffe3bc568a6")
    }
}