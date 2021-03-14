package com.jqk.mydemo.kotlin.lambda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LambdaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Test().setOnClickListener {

        }
    }
}