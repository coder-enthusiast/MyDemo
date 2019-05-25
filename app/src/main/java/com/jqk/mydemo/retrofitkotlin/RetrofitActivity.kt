package com.jqk.mydemo.retrofitkotlin

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityRetrofitBinding
import com.jqk.mydemo.mvvmnew.news.News
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by jiqingke
 * on 2019/1/15
 */

class RetrofitActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName

    lateinit var b: ActivityRetrofitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_retrofit)
        b.view = this

    }

    fun start(view: View) {
        var call = RetrofitHttpRequest.retrofitService.getNew(type = "top", key = "93ff5c6fd6dc134fc69f6ffe3bc568a6")
        call.enqueue(object : Callback<News> {
            override fun onFailure(call: Call<News>, t: Throwable) {

            }

            override fun onResponse(call: Call<News>, response: retrofit2.Response<News>) {
                Log.d(TAG, "response = " + response.body().toString())
            }
        })
    }
}