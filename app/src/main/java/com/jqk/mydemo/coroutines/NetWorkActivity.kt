package com.jqk.mydemo.coroutines

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R

class NetWorkActivity : AppCompatActivity() {
    lateinit var start: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_network)
        start = findViewById(R.id.start)

        start.setOnClickListener {

        }
    }
}