package com.jqk.mydemo.jetpack.room

import androidx.room.ColumnInfo

/**
 * Created by jiqingke
 * on 2019/2/19
 */
data class NameTuple(
        @ColumnInfo(name = "first_name") var firstName: String?,
        @ColumnInfo(name = "last_name") var lastName: String?
)