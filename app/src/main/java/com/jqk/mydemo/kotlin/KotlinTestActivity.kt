package com.jqk.mydemo.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.jqk.commonlibrary.util.L

import com.jqk.mydemo.R
import com.jqk.mydemo.kotlin.kotlinconstruction.Test2
import com.jqk.mydemo.kotlin.kotlinconstruction.Test3
import com.jqk.mydemo.kotlin.kotlinconstruction.Test4

class KotlinTestActivity : AppCompatActivity() {
    // 声明时没有直接赋值，用?标记或者使用lateinit修饰一下，lateinit是延迟加载
    private var button: Button? = null
    private lateinit var str: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlintest)

        button = findViewById(R.id.button)

        // !!.如果button空指针，报nullpoint错误
        button!!.setOnClickListener {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        }
        // ?.如果button空则右边不执行，也不报错
        button?.setOnClickListener {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        }

        val test2 = Test2()
        test2.setaSum { i, i2 ->
            val a = i + i2
            L.d("a = " + a)
            a
        }

        test2.listener = object : Test2.Listener {
            override fun onClick(i: Int) {
                L.d("onClick = " + i)
            }

            override fun onLongClick(i: Int) {
                L.d("onLongClick = " + i)
            }
        }

        test2.abc()

        val test3 = Test3()
        test3.callBack { function, function2 ->
            function {
                L.d("function = " + it)
            }

            function2 { value1, value2 ->
                L.d("function2 = " + value1 + value2)
            }
        }

        test3.abc()

        val test4 = Test4()
        test4.setListener(Test4.CallbacksImpl({
            L.d("it = " + it)
        }, {
            L.d("----打印错误结果---->onError()")
        }))

        test4.abc()
    }
}

// 派生类初始化顺序
open class Base(val name: String) {

    init {
        println("Initializing Base")
    }

    open val size: Int = name.length.also { println("Initializing size in Base: $it") }
}

class Derived(
        name: String,
        val lastName: String

) : Base(name.capitalize().also { println("Argument for Base: $it") }) {

    init {
        println("Initializing Derived")
    }

    override val size: Int =
            (super.size + lastName.length).also { println("Initializing size in Derived: $it") }
}
//运行结果
//Constructing Derived("hello", "world")
//Argument for Base: Hello          基类构造函数
//Initializing Base                 基类初始化块
//Initializing size in Base: 5      基类属性初始化
//Initializing Derived              子类初始化块
//Initializing size in Derived: 10  子类属性初始化块

//常用标准函数

//let
//let扩展函数的实际上是一个作用域函数，当你需要去定义一个变量在一个特定的作用域范围内，let函数的是一个不错的选择；
//let函数另一个作用就是可以避免写一些判断null的操作。
//object.let{
//    it.todo()//在函数体内使用it替代object对象去访问其公有的属性和方法
//    ...
//}
//
////另一种用途 判断object为null的操作
//object?.let{//表示object不为null的条件下，才会去执行let函数体
//    it.todo()
//}

// with
//with(object){
//    //todo
//}
//适用于调用同一个类的多个方法时，可以省去类名重复，直接调用类的方法即可

// run
//object.run{
////todo
//}
//适用于let,with函数任何场景。因为run函数是let,with两个函数结合体，准确来说它弥补了let函数在函数体内必须使用it参数替代对象，在run函数中可以像with函数一样可以省略，直接访问实例的公有属性和方法，另一方面它弥补了with函数传入对象判空问题，在run函数中可以像let函数一样做判空处理

//apply
//object.apply{
////todo
//}
//从结构上来看apply函数和run函数很像，唯一不同点就是它们各自返回的值不一样，
//run函数是以闭包形式返回最后一行代码的值，而apply函数的返回的是传入对象的本身。
//apply一般用于一个对象实例初始化的时候，需要对对象中的属性进行赋值。
//或者动态inflate出一个XML的View的时候需要给View绑定数据也会用到，这种情景非常常见。

//also
//object.also{
////todo
//}
//also函数的结构实际上和let很像唯一的区别就是返回值的不一样，let是以闭包的形式返回，
//返回函数体内最后一行的值，如果最后一行为空就返回一个Unit类型的默认值。
//而also函数返回的则是传入对象的本身