package com.jqk.mydemo.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.R
import com.jqk.commonlibrary.util.L
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.*
import kotlin.concurrent.thread


class DemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        firstMain()
//        secondMain()
//        thirdMain()
//        fourthMain()
//        fifthMain()
//        sixthMain()
    }

    fun firstMain() {
        GlobalScope.launch {
            // 在后台启动一个新的协程并继续
            delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
            com.jqk.commonlibrary.util.L.d("World!") // 在延迟后打印输出
        }
        com.jqk.commonlibrary.util.L.d("Hello,") // 协程已在等待时主线程还在继续
        Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
    }

    fun secondMain() {
        GlobalScope.launch {
            // 在后台启动一个新的协程并继续
            delay(1000L)
            com.jqk.commonlibrary.util.L.d("World!")
        }
        com.jqk.commonlibrary.util.L.d("Hello,") // 主线程中的代码会立即执行
        runBlocking {
            // 但是这个表达式阻塞了主线程
            delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
        }
    }

    fun thirdMain() = runBlocking<Unit> {
        // 开始执行主协程
        GlobalScope.launch {
            // 在后台启动一个新的协程并继续
            delay(1000L)
            com.jqk.commonlibrary.util.L.d("World!")
        }
        com.jqk.commonlibrary.util.L.d("Hello,") // 主协程在这里会立即执行
        delay(2000L)      // 延迟 2 秒来保证 JVM 存活
    }

    fun fourthMain() = runBlocking<Unit> {
        // 开始执行主协程
        val job = GlobalScope.launch {
            // 启动一个新协程并保持对这个作业的引用
            delay(1000L)
            com.jqk.commonlibrary.util.L.d("World!")
        }
        com.jqk.commonlibrary.util.L.d("Hello,")
        job.join() // 等待直到子协程执行结束
    }

    fun fifthMain() = runBlocking {
        // this: CoroutineScope
        launch {
            // 在 runBlocking 作用域中启动一个新协程
            delay(1000L)
            com.jqk.commonlibrary.util.L.d("World!")
        }
        com.jqk.commonlibrary.util.L.d("Hello,")
    }

    fun sixthMain() = runBlocking {
        // this: CoroutineScope
        launch {
            delay(200L)
            com.jqk.commonlibrary.util.L.d("Task from runBlocking")
        }

        coroutineScope {
            // 创建一个协程作用域
            launch {
                delay(500L)
                com.jqk.commonlibrary.util.L.d("Task from nested launch")
            }

            delay(100L)
            com.jqk.commonlibrary.util.L.d("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
        }

        com.jqk.commonlibrary.util.L.d("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
    }
}