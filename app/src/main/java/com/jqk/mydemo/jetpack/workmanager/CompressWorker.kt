package com.jqk.mydemo.jetpack.workmanager

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jqk.mydemo.jetpack.room.AppDatabase
import com.jqk.mydemo.jetpack.room.User

/**
 * Created by jiqingke
 * on 2019/2/20
 */
class CompressWorker(context: Context, params: WorkerParameters)
    : Worker(context, params) {

    val db: AppDatabase by lazy {
        Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
        ).build()
    }

    init {

    }

    override fun doWork(): Result {

        // Do the work here--in this case, compress the stored images.
        // In this example no parameters are passed; the task is
        // assumed to be "compress the whole library."

        // Indicate success or failure with your return value:
        val id = inputData.getInt("id", 0)
        Log.d("jiqingke", "传入的id = " + id)
        val user = User(id, "", "", 10, "","", "")
        val data = Data.Builder().putLong("data", db.userDao().insertUser(user)).build()
        return Result.success(data)

        // (Returning Result.retry() tells WorkManager to try this task again
        // later; Result.failure() says not to try again.)

    }
}