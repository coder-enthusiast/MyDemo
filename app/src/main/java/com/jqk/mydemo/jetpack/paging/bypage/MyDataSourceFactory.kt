package com.jqk.mydemo.jetpack.paging.bypage

import androidx.paging.DataSource
import com.jqk.mydemo.jetpack.paging.Bean

/**
 * Created by jiqingke
 * on 2019/2/15
 */
class MyDataSourceFactory() : DataSource.Factory<Int, Bean>() {
    override fun create(): DataSource<Int, Bean> {
        return MyPageKeyedDataSource()
    }
}