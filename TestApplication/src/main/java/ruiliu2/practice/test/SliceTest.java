package ruiliu2.practice.test;

import java.util.Scanner;

/**
 * Created by casa on 2017/6/8.
 */
public class SliceTest {
    private static int[] originalArr = null;

    private static boolean slice() {
        //TODO 判空
        if (originalArr == null || originalArr.length < 7) {
            return false;
        }

        int leftIndex = 0;
        int rightIndex = originalArr.length - 1;

        int leftSum = originalArr[leftIndex];
        int rightSum = originalArr[rightIndex];

        //TODO 从前后开始遍历，首先找到和相等的首尾两个子集合
        while (leftIndex < rightIndex) {

            if (leftSum < rightSum) {
                leftIndex++;
                leftSum += originalArr[leftIndex];
            } else if (leftSum > rightSum) {
                rightIndex--;
                rightSum += originalArr[rightIndex];
            } else {
                return rightIndex - leftIndex >= 3 && findMiddle(leftIndex + 2, rightIndex - 2);
            }
        }

        return false;
    }

    private static boolean findMiddle(int startIndex, int endIndex) {
        int leftSum = originalArr[startIndex];
        int rightSum = originalArr[endIndex];

        while (startIndex < endIndex) {
            if (leftSum < rightSum) {
                startIndex++;
                leftSum += originalArr[startIndex];
            } else if (leftSum > rightSum) {
                endIndex--;
                rightSum += originalArr[endIndex];
            } else {
                return endIndex - startIndex == 2;
            }
        }

        return false;
    }

    public static void main(String... args) {
        originalArr = new int[]{2, 5, 1, 1, 1, 1, 4, 1, 7, 3, 7};

        System.out.println(slice());
    }

}
