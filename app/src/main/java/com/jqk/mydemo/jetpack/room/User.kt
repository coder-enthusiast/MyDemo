package com.jqk.mydemo.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by jiqingke
 * on 2019/2/19
 */
//@Entity
//// 复合主键
////@Entity(primaryKeys = arrayOf("firstName", "lastName"))
//// 数据库表名
@Entity(tableName = "users")
data class User(
        // 自动分配ID
//        @PrimaryKey(autoGenerate = true)
        @PrimaryKey
        val id: Int,
        // 列名称
        @ColumnInfo(name = "first_name")
        val firstName: String?,
        @ColumnInfo(name = "last_name")
        val lastName: String?,
        val age: Int,
        val region: String
        // 忽略字段
//        @Ignore
//        var picture: Bitmap?
)