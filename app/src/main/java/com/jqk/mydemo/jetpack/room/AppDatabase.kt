package com.jqk.mydemo.jetpack.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by jiqingke
 * on 2019/2/19
 */
@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}