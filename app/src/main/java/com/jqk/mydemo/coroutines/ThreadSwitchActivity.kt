package com.jqk.mydemo.coroutines

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R
import com.jqk.mydemo.util.L
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ThreadSwitchActivity : AppCompatActivity() {
    lateinit var start: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_threadswitch)

        start = findViewById(R.id.start)
        start.setOnClickListener {
            newSingleThreadContext("Ctx1").use { ctx1 ->
                newSingleThreadContext("Ctx2").use { ctx2 ->
                    runBlocking(ctx1) {
                        L.d("Started in ctx1")
                        withContext(ctx2) {
                            L.d("Working in ctx2")
                        }
                        L.d("Back to ctx1")
                    }
                }
            }
        }
    }
}