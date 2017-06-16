package ruiliu2.practice.test;

/**
 * Created by casa on 2017/6/8.
 */
public class QuickSort {

    private static int[] originalArr = null;

    /**
     * 实现排序具体功能
     */
    private static void sort(int startIndex, int endIndex) {
        //TODO 判断返回条件
        if (originalArr == null || originalArr.length <= 1 || endIndex - startIndex < 1) {
            return;
        }

        //具体遍历游标
        int leftIndex = startIndex;
        int rightIndex = endIndex;
        int flag = originalArr[leftIndex];
        int flagIndex = startIndex;

        //TODO 循环遍历
        while (leftIndex < rightIndex) {
            //先从后向前遍历寻找到比标记数小的数进行交换，注意交换后游标不移动，否则会误伤还未向前移动的游标
            while (leftIndex < rightIndex) {
                if (originalArr[rightIndex] < flag) {
                    originalArr[flagIndex] = originalArr[rightIndex];
                    originalArr[rightIndex] = flag;
                    flagIndex = rightIndex;
                    break;
                }
                rightIndex--;
            }

            while (leftIndex < rightIndex) {
                if (originalArr[leftIndex] > flag) {
                    originalArr[flagIndex] = originalArr[leftIndex];
                    originalArr[leftIndex] = flag;
                    flagIndex = leftIndex;
                    break;
                }
                leftIndex++;
            }
        }


        //TODO 递归调用
        sort(startIndex, flagIndex - 1);
        sort(flagIndex + 1, endIndex);
    }

    /**
     * 主程序入口
     */
    public static void main(String... args) {
        originalArr = new int[]{1, 2, 3, 4, 5};

        sort(0, originalArr.length - 1);

        for (int i = 0; i < originalArr.length; i++) {
            System.out.print(originalArr[i] + " ");
        }
    }
}
