package ruiliu2.practice.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by casa on 2017/6/8.
 */
public class HeapSort {

    private static int[] originalArr = null;

    public static void main(String... args) {
        originalArr = new int[]{87, 6, 66, 32, 17, 65, 53, 9, 122};
        // TODO: 2017/6/8 根据数组初始化堆
        initHeap();
        switchAndRotate(originalArr.length - 1);
        for (int i = 0; i < originalArr.length; i++) {
            System.out.print(originalArr[i] + " ");
        }

    }

    private static void initHeap() {
        //TODO: 对数组进行一次排序，按照大根堆的，需要传递根结点的下标，左右子树的下标，进行判断并交换数据，假设根为i，则左子树节点为2i+1
        //TODO: 右子树的节点下标为2i+2
        for (int i = (originalArr.length - 2) / 2; i >= 0; i--) {
            rotateHeap(i, originalArr.length - 1);
        }
    }

    private static void switchAndRotate(int endIndex) {

        if (endIndex == 0) {
            return;
        }
        int temp = originalArr[0];
        originalArr[0] = originalArr[endIndex];
        originalArr[endIndex] = temp;
        for (int i = 0; i <= (endIndex - 2) / 2; i++) {
            rotateHeap(i, endIndex - 1);
        }

        switchAndRotate(endIndex - 1);
    }

    private static void rotateHeap(int rootIndex, int endIndex) {
        int leftChildIndex = rootIndex * 2 + 1;
        int rightChildIndex = rootIndex * 2 + 2;
        int leftChildValue;
        int rightChildValue;
        int rootValue = originalArr[rootIndex];
        if (leftChildIndex > endIndex) {
            return;
        } else if (rightChildIndex > endIndex) {
            rightChildValue = -1;
        } else {
            rightChildValue = originalArr[rightChildIndex];
        }
        leftChildValue = originalArr[leftChildIndex];


        if (rootValue < leftChildValue || rootValue < rightChildValue) {
            if (rightChildValue <= leftChildValue) {
                originalArr[rootIndex] = leftChildValue;
                originalArr[leftChildIndex] = rootValue;
            } else {
                originalArr[rootIndex] = rightChildValue;
                originalArr[rightChildIndex] = rootValue;
            }
        }
    }
}
