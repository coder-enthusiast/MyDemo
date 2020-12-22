package com.jqk.mydemo.koin

class HelloRepositoryImpl : HelloRepository{
    override fun giveHello(): String  = "Hello Koin"
}