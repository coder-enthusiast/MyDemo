package com.jqk.mydemo.algorithm

class SolutionKotlin {
    // 最长回文子串
//    输入: "babad"
//    输出: "bab"
//    注意: "aba"也是一个有效答案。
//    输入: "cbbd"
//    输出: "bb"
    /**
     * 思路：首先两个for循环遍历所有子串可能，判断子串收尾字符是否相等，如果相等，则判断是不是回文字符串；
     * 将删选出的回文字符串放到一个list中，找出最长的按个回文子串
     */
    fun longestPalindrome(s: String): String {
        if (s == "") {
            return ""
        }
        var result = ""
        var length = 1
        var array = mutableListOf<String>()
        for (i in 0..s.length - 1) {
            val first = s[i]
            for (j in i..s.length - 1) {
                val last = s[j]
                if (first == last) {
                    val str = s.subSequence(i, j + 1).toString()
                    if (str.length < length) {
                        continue
                    }
                    println("要验证的字符串 = " + str)
                    println("str.length / 2 = " + str.length / 2)
                    if (str.length == 1 || str.length == 2) {
                        array.add(str)
                        if (length < str.length) {
                            length = str.length
                        }
                    }
                    for (n in 1..str.length / 2) {
                        if (str[n] == str[str.length - 1 - n]) {
                            if (n == str.length / 2) {
                                println("回文字符串 = " + str)
                                array.add(str)
                                if (length < str.length) {
                                    length = str.length
                                }
                            }
                        } else {
                            break
                        }
                    }
                }
            }
        }

        if (array.size == 1) {
            return array[0]
        }
        result = array[0]
        for (i in 0..array.size - 1) {
            val n = result.length
            val m = array[i].length
            if (m > n) {
                result = array[i]
            }
        }
//        println("回文字符串 = " + array.toString())
        return result
    }
// 两个排序数组的中位数
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        var array = mutableListOf<Int>()
        for (i in 0..nums1.size - 1) {
            for (j in 0..nums2.size - 1) {
//                if (nums1[i] < ) {
//
//                }
            }
        }
        return 0.0
    }
}

fun main(args: Array<String>) {
    val solution = SolutionKotlin()
//    println(solution.longestPalindrome("abacab"))
}