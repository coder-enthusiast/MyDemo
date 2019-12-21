package com.jqk.jetpacklibrary.paging.bypage

import android.util.Log
import com.jqk.jetpacklibrary.paging.Bean

/**
 * Created by jiqingke
 * on 2019/2/15
 */
class DataRepository(val pageSize: Int,
                     val totalSize: Int) {

    val datas: List<Bean>

    init {
        datas = mutableListOf()
        for (i in 1..totalSize) {
            datas.add(Bean(i, "" + i))
        }
    }

    fun getPageData(key: Int): List<Bean> {
        Log.d("jiqingke", "加载数据 " + key)
        val result = mutableListOf<Bean>()
        for (i in (key - 1) * pageSize..key * pageSize - 1) {
            result.add(datas[i])
        }

        return result
    }
}