package com.jqk.mydemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jiqingke
 * on 2019/3/6
 */
public class TestJava {
    public String name = "abc";

    private Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();


    public static void main(String[] args) {
        TestJava test = new TestJava();
        TestJava testB = new TestJava();
        System.out.println(test.equals(testB) + "," + test.name.equals(testB.name));

        try {
            test.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void await() {
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
