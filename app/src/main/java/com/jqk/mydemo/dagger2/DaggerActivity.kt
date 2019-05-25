package com.jqk.mydemo.dagger2

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R
import com.jqk.mydemo.databinding.ActivityDaggerBinding

/**
 * Created by jiqingke
 * on 2019/1/17
 */

class DaggerActivity : AppCompatActivity() {
    lateinit var b: ActivityDaggerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_dagger)

        val coffeeShop = DaggerCoffeeShop.builder().build()
        coffeeShop.maker().brew()
    }
}