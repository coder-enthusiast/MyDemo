package com.jqk.mydemo.jetpack.paging.byitem

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import com.jqk.mydemo.jetpack.paging.Bean

/**
 * Created by jiqingke
 * on 2019/2/17
 */
class MyItemKeyedDataSource : ItemKeyedDataSource<Int, Bean>() {

    val pageSize = 10
    val totleSize = 100

    val dataRepository: DataRepository by lazy {
        DataRepository(pageSize, totleSize)
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Bean>) {
        callback.onResult(dataRepository.getPageData(1))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Bean>) {
        Log.d("jiqingke", "loadAfter" + params.key)
        if (params.key == totleSize) {
            return
        }
        callback.onResult(dataRepository.getItemData(params.key))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Bean>) {

    }

    override fun getKey(item: Bean): Int {
        Log.d("jiqingke", "getKey" + item.id)
        return item.id
    }
}