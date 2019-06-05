package com.jqk.mydemo;

/**
 * Created by jiqingke
 * on 2019/3/6
 */
public class TestJava {
    public String name = "abc";

    public static void main(String[] args) {
        TestJava test = new TestJava();
        TestJava testB = new TestJava();
        System.out.println(test.equals(testB) + "," + test.name.equals(testB.name));
    }

}
