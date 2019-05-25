package com.jqk.mydemo.algorithm;

public class Solution {
    public static void main(String[] args) {
//        System.out.println(isPalindrome(1001));
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

    public static boolean isPalindrome(int x) {

//        if (x < 0) {
//            return false;
//        } else {
//            StringBuffer stringBuffer = new StringBuffer(x + "");
//            if (stringBuffer.length() == 1) {
//                return true;
//            }
//            for (int i = 0; i < stringBuffer.length() / 2; i++) {
//                String a1 = stringBuffer.charAt(i) + "";
//                String a2 = stringBuffer.charAt(stringBuffer.length() - 1 - i) + "";
//                System.out.println("a1 = " + a1);
//                System.out.println("a2 = " + a2);
//                if (!a1.equals(a2)) {
//                    return false;
//                }
//            }
//            return true;
//        }

//        首先，我们应该处理一些临界情况。所有负数都不可能是回文，例如：-123 不是回文，因为 - 不等于 3。所以我们可以对所有负数返回 false。
//
//        现在，让我们来考虑如何反转后半部分的数字。 对于数字 1221，如果执行 1221 % 10，我们将得到最后一位数字 1，要得到倒数第二位数字，我们可以先通过除以 10 把最后一位数字从 1221 中移除，1221 / 10 = 122，再求出上一步结果除以10的余数，122 % 10 = 2，就可以得到倒数第二位数字。如果我们把最后一位数字乘以10，再加上倒数第二位数字，1 * 10 + 2 = 12，就得到了我们想要的反转后的数字。 如果继续这个过程，我们将得到更多位数的反转数字。
//
//        现在的问题是，我们如何知道反转数字的位数已经达到原始数字位数的一半？
//
//        我们将原始数字除以 10，然后给反转后的数字乘上 10，所以，当原始数字小于反转后的数字时，就意味着我们已经处理了一半位数的数字。

        // 特殊情况：
        // 如上所述，当 x < 0 时，x 不是回文数。
        // 同样地，如果数字的最后一位是 0，为了使该数字为回文，
        // 则其第一位数字也应该是 0
        // 只有 0 满足这一属性
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revertedNumber = 0;
        while (x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }

        // 当数字长度为奇数时，我们可以通过 revertedNumber/10 去除处于中位的数字。
        // 例如，当输入为 12321 时，在 while 循环的末尾我们可以得到 x = 12，revertedNumber = 123，
        // 由于处于中位的数字不影响回文（它总是与自己相等），所以我们可以简单地将其去除。
        return x == revertedNumber || x == revertedNumber / 10;
    }

    public static int maxArea(int[] height) {

//        方法二：双指针法
//                算法
//
//        这种方法背后的思路在于，两线段之间形成的区域总是会受到其中较短那条长度的限制。
// 此外，两线段距离越远，得到的面积就越大。

//        我们在由线段长度构成的数组中使用两个指针，
// 一个放在开始，一个置于末尾。 此外，我们会使用变量 maxareamaxarea 来持续存储到目前为止所获得的最大面积。
// 在每一步中，我们会找出指针所指向的两条线段形成的区域，更新 maxareamaxarea，
// 并将指向较短线段的指针向较长线段那端移动一步。

        int result = 0;
        int result1;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                result1 = (j - i) * (height[j] > height[i] ? height[i] : height[j]);
                if (result < result1) {
                    result = result1;
                }
            }
        }

        return result;
    }
}
