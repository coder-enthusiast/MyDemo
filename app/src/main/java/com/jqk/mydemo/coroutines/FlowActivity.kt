package com.jqk.mydemo.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jqk.commonlibrary.util.L
import com.jqk.mydemo.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class FlowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_flow)

        GlobalScope.launch {
            //            foo().collect { value -> L.d("flow value = " + value) }
//
//            withTimeoutOrNull(250) {
//                // 在 250 毫秒后超时
//                foo().collect { value -> L.d("flow value = " + value) }
//            }
//            L.d("Done")

//            (1..3).asFlow().collect { value -> L.d("flow value = " + value) }
//
//            (1..3).asFlow() // 一个请求流
//                    .transform { request ->
//                        emit("Making request $request")
//                        emit(request)
//                    }
//                    .collect { response -> L.d("flow value = " + response) }

//            val sum = (1..5).asFlow()
//                    .map { it * it } // 数字 1 至 5 的平方
//                    .reduce { a, b -> a + b } // 求和（末端操作符）
//            L.d("flow value = " + sum)

//            (1..5).asFlow()
//                    .filter {
//                        L.d("flow value = " + "Filter $it")
//                        it % 2 == 0
//                    }
//                    .map {
//                        L.d("flow value = " + "Map $it")
//                        "string $it"
//                    }.collect {
//                        L.d("flow value = " + "Collect $it")
//                    }

            val nums = (1..3).asFlow() // 数字 1..3
            val strs = flowOf("one", "two", "three") // 字符串
            nums.zip(strs) { a, b -> "$a -> $b" } // 组合单个字符串
                    .collect { L.d("flow value = " + it) } // 收集并打印
        }

//        main()
//        main2()
    }

    fun foo(): Flow<Int> = flow {
        // 流构建器
        for (i in 1..3) {
            delay(100) // 假装我们在这里做了一些有用的事情
            emit(i) // 发送下一个值
        }
    }

    fun main() = GlobalScope.launch {
        //        (1..3).asFlow() // 一个请求流
//                .map { request -> "我是字符串" + request }
//                .collect { response -> L.d("flow value = " + response) }

        (1..3).asFlow() // 一个请求流
                .map { request -> performRequest(request) }
                .collect { response -> L.d("flow value = " + response) }
    }

    fun performRequest(request: Int): String {
        return "response $request"
    }

    fun numbers(): Flow<Int> = flow {
        try {
            emit(1)
            emit(2)
            L.d("flow value = " + "This line will not execute")
            emit(3)
        } finally {
            L.d("flow value = " + "Finally in numbers")
        }
    }

    fun main2() = GlobalScope.launch {
        numbers()
                .take(2) // 只获取前两个
                .collect { value -> L.d("flow value = " + value) }
    }
}