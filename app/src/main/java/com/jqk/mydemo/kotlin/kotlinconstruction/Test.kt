package com.jqk.mydemo.kotlin.kotlinconstruction

class Student : Person {
    //        constructor(name: String, age: String)
    constructor(school: String) : super()
}

open class Teacher {
    constructor(name: String)
}

class Doctor : Teacher {
    constructor(name: String) : super(name)
}

class Ha(): Person() {
    var number = "name"
    val firstProperty = "First property: $number".also(::println)
}

class Test {

    fun main() {
//        var person = Person()

        var person = Person()

        var student = Student("")

        var student2 = Student("")

        var teacher = Teacher("")

        val prop = "abc"::length
        println(prop.get())

        val strs = listOf("a", "bc", "def")
        println(strs.map(String::length))

        fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
            return { x -> f(g(x)) }
        }
    }
}