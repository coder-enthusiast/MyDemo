package com.jqk.mydemo.algorithm;

public class Reverse {

    public static void main(String[] args) {
        System.out.print(reverse(1534236469));
    }

    /**
     * 翻转整数
     * @param x
     * @return
     */
    public static int reverse(int x) {

        boolean negative = false;
        boolean overflow = false;

        if (x < 0) {
            negative = true;
        } else {
            negative = false;
        }

        String str = String.valueOf(x);
        char[] strs = str.toCharArray();

        int a = 0;
        char[] newStrs;
        if (negative) {
            a = 1;
            newStrs = new char[strs.length - a];

            for (int i = a; i < strs.length; i++) {
                newStrs[i - 1] = strs[strs.length - i];
            }

        } else {
            a = 0;
            newStrs = new char[strs.length - a];

            for (int i = a; i < strs.length; i++) {
                newStrs[i] = strs[strs.length - 1 - i];
            }
        }

        System.out.println("newStrs.lang = " + newStrs.length);
        for (char c : newStrs) {
            System.out.println(c);
        }

        int result = 0;

        for (int i = 0; i < newStrs.length; i++) {
            int n = (int) (Math.pow(10, newStrs.length - 1 - i));
            int j = Integer.parseInt(String.valueOf(newStrs[i]));
            int m = j * n;
            System.out.println("j = " + j);
            System.out.println("n = " + n);
            System.out.println("m = " + m);
            if (m <= 0 && n != 0) {
                overflow = true;
                break;
            }
            result = result + m;
        }

        if (negative) {
            result = -result;
        }

        if (overflow) {
            result = 0;
        }
        return result;
    }

    /**
     * 回文数
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        // 所有负数都不是回文数

        return false;
    }
}
