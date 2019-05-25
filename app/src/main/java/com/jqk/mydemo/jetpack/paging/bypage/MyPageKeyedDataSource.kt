package com.jqk.mydemo.jetpack.paging.bypage

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.jqk.mydemo.jetpack.paging.Bean

/**
 * Created by jiqingke
 * on 2019/2/15
 */
class MyPageKeyedDataSource : PageKeyedDataSource<Int, Bean>() {

    val pageSize = 10
    val totleSize = 100

    val dataRepository: DataRepository by lazy {
        DataRepository(pageSize, totleSize)
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Bean>) {
        callback.onResult(dataRepository.getPageData(1), null, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Bean>) {
        if (params.key > totleSize / pageSize) {
            return
        }
        Log.d("jiqingke", "params.key = " + params.key)
        callback.onResult(dataRepository.getPageData(params.key), params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Bean>) {

    }
}