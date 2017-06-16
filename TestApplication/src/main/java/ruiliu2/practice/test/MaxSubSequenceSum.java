package ruiliu2.practice.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by casa on 2017/6/8.
 */
public class MaxSubSequenceSum {

    private static int[] originalArr = {-2, 11, -4, 13, -5, -2};

    public static void main(String... args) {
        int totalSum = originalArr[0];
        int loopTemp = originalArr[0];
        for (int i = 1; i < originalArr.length; i++) {
            int loopResult = loopTemp + originalArr[i];
            if (loopResult > originalArr[i]) {
                loopTemp = loopResult;
            } else {
                loopTemp = originalArr[i];
            }

            if (loopTemp > totalSum) {
                totalSum = loopTemp;
            }
        }

        System.out.println(totalSum);
    }
}
