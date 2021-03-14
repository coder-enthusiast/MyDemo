package com.jqk.jetpacklibrary.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by jiqingke
 * on 2019/2/19
 */
@Database(entities = [User::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}