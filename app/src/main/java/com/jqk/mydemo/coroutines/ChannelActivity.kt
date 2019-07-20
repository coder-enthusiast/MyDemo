package com.jqk.mydemo.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jqk.mydemo.util.L
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce

class ChannelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            val numbers = produceNumbers() // 从 1 开始生产整数
            val squares = square(numbers) // 对整数做平方
            for (i in 1..5) L.d(squares.receive().toString()) // 打印前 5 个数字
            L.d("Done!") // 我们的操作已经结束了
            coroutineContext.cancelChildren() // 取消子协程

            withTimeoutOrNull(20000L) {
                repeat(1000) { i ->
                    L.d("I'm sleeping $i ...")
                    delay(500L)
                }
            }
        }

    }

    fun CoroutineScope.produceNumbers() = produce<Int> {
        var x = 1
        while (true) send(x++) // 从 1 开始的无限的整数流
    }

    fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        for (x in numbers) send(x * x)
    }

    suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
        while (true) {
            delay(time)
            channel.send(s)
        }
    }
}