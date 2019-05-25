package com.jqk.mydemo.eventbus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast

import com.jqk.mydemo.R

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBusActivity : AppCompatActivity() {
    private var send: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventbus)
        send = findViewById(R.id.send)

        send!!.setOnClickListener {
            EventBus.getDefault().post(MessageEvent())
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun haha(event: MessageEvent) {
        Log.d("tiankong", "收到消息")
        Toast.makeText(this, "收到消息", Toast.LENGTH_SHORT).show()
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}
