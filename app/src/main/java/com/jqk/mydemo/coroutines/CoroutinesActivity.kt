package com.jqk.mydemo.coroutines

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R
import com.jqk.mydemo.util.L
import kotlinx.android.synthetic.main.activity_coroutines.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.channels.*
import kotlin.system.measureTimeMillis

/**
 * 参考 https://github.com/hltj/kotlinx.coroutines-cn/blob/master/ui/coroutines-guide-ui.md
 */
class CoroutinesActivity : ScopedAppActivity() {
    lateinit var title: TextView
    lateinit var start: Button
    lateinit var cancel: Button
    lateinit var job: Job
    lateinit var eventActor: SendChannel<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        title = findViewById(R.id.title)
        start = findViewById(R.id.start)
        cancel = findViewById(R.id.cancel)

//        start.setOnClickListener {
//            job = GlobalScope.launch(Dispatchers.Main) { // 在主线程中启动协程
//                for (i in 10 downTo 1) {
//                    title.text = "Countdown $i..."
//                    delay(500)
//                }
//                title.text = "Done!"
//            }
//        }

//        cancel.setOnClickListener {
//            job.cancel()
//        }
        /**************************************************************************/
//        fun View.onClick(action: suspend () -> Unit) {
//            setOnClickListener(object : View.OnClickListener {
//                override fun onClick(v: View?) {
//                    job = GlobalScope.launch(Dispatchers.Main) {
//                        action()
//                    }
//                }
//            })
//        }
//
//        start.onClick {
//            for (i in 10 downTo 1) { // 从 10 到 1 的倒记时
//                title.text = "Countdown $i ..." // 更新文本
//                delay(500) // 等待半秒钟
//            }
//            title.text = "Done!"
//        }
//
//        cancel.setOnClickListener {
//            job.cancel()
//        }
        /**************************************************************************/
//        fun View.onClick(action: suspend (View) -> Unit) {
//            eventActor = GlobalScope.actor<View>(Dispatchers.Main) {
//                for (event in channel) action(event)
//            }
//
//            setOnClickListener {
//                eventActor.offer(it)
//            }
//        }
//
//        start.onClick {
//            for (i in 10 downTo 1) {
//                title.text = "Countdown $i..."
//                delay(500)
//            }
//            title.text = "Done!"
//        }
//
//        cancel.setOnClickListener {
//
//        }
        /**************************************************************************/
//        fun View.onClick(action: suspend (View) -> Unit) {
//            eventActor = GlobalScope.actor<View>(Dispatchers.Main, capacity = Channel.CONFLATED) {
//                for (event in channel) action(event)
//            }
//
//            setOnClickListener {
//                eventActor.offer(it)
//            }
//        }
//
//        start.onClick {
//            for (i in 10 downTo 1) {
//                title.text = "Countdown $i..."
//                delay(500)
//            }
//            title.text = "Done!"
//        }
//
//        cancel.setOnClickListener {
//
//        }
        /**************************************************************************/
//        fun View.onClick(action: suspend (View) -> Unit) {
//            eventActor = GlobalScope.actor<View>(Dispatchers.Main, capacity = Channel.CONFLATED) {
//                for (event in channel) action(event)
//            }
//
//            setOnClickListener {
//                eventActor.offer(it)
//            }
//        }
//
//        start.onClick {
//            for (i in 10 downTo 1) {
//                title.text = "Countdown $i..."
//                delay(500)
//            }
//            title.text = "Done!"
//        }
//
//        cancel.setOnClickListener {
//
//        }
//
//        fun fib(x: Int): Int =
//                if (x <= 1) x else fib(x - 1) + fib(x - 2)
//
//        fun setup(hello: TextView, fab: View) {
//            var result = "none" // the last result
//            // counting animation
//            GlobalScope.launch(Dispatchers.Main) {
//                var counter = 0
//                while (true) {
//                    hello.text = "${++counter}: $result"
//                    delay(100) // update the text every 100ms
//                }
//            }
//            // compute the next fibonacci number of each click
//            var x = 1
//            fab.onClick {
//                result = "fib($x) = ${fib(x)}"
//                x++
//            }
//        }
//
//        setup(title, start)
        /**************************************************************************/
//        start.onClick {
//            for (i in 10 downTo 1) {
//                title.text = "Countdown $i..."
//                delay(500)
//            }
//            title.text = "Done!"
//        }
//
//        cancel.setOnClickListener {
//
//        }
//
//        setup(title, start)
        /**************************************************************************/
//        fun setup(hello: TextView, start: View) {
//            start.setOnClickListener {
//                L.d("Before launch")
//                GlobalScope.launch(Dispatchers.Main) {
//                    L.d("Inside coroutine")
//                    delay(100)
//                    L.d("After delay")
//                }
//                L.d("After launch")
//            }
//        }
//
//        setup(title, start)
        /**************************************************************************/
        fun setup(hello: TextView, start: View) {
            start.setOnClickListener {
                L.d("Before launch")
                GlobalScope.launch(Dispatchers.Main, CoroutineStart.UNDISPATCHED) { // <--- 通知这次改变
                    L.d("Inside coroutine")
                    delay(100)                            // <--- 这里是协程挂起的地方
                    L.d("After delay")
                }
                L.d("After launch")
            }
        }

        setup(title, start)
    }

//    fun View.onClick(action: suspend (View) -> Unit) {
//        eventActor = GlobalScope.actor<View>(Dispatchers.Main, capacity = Channel.CONFLATED) {
//            for (event in channel) action(event)
//        }
//
//        setOnClickListener {
//            eventActor.offer(it)
//        }
//    }

//    suspend fun fib(x: Int): Int = withContext(Dispatchers.Default) {
//        if (x <= 1) x else fib(x - 1) + fib(x - 2)
//    }

//    fun setup(hello: TextView, fab: View) {
//        var result = "none" // the last result
//        // counting animation
//        GlobalScope.launch(Dispatchers.Main) {
//            var counter = 0
//            while (true) {
//                hello.text = "${++counter}: $result"
//                delay(100) // update the text every 100ms
//            }
//        }
//        // compute the next fibonacci number of each click
//        var x = 1
//        fab.onClick {
//            result = "fib($x) = ${fib(x)}"
//            x++
//        }
//    }
//
//    suspend fun fib(x: Int): Int = withContext(Dispatchers.Default) {
//        fibBlocking(x)
//    }
//
//    fun fibBlocking(x: Int): Int =
//            if (x <= 1) x else fibBlocking(x - 1) + fibBlocking(x - 2)

}