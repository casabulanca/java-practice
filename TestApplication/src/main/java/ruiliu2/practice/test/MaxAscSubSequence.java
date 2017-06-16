package ruiliu2.practice.test;

/**
 * Created by casa on 2017/6/8.
 */
public class MaxAscSubSequence {

    private static int[] originalArr = {5, 4, 3, 2, 1, 2, 3, 4, 5, 6};

    public static void main(String... args) {
        int[] LIS = new int[originalArr.length];
        LIS[0] = 1;

        for (int i = 1; i < originalArr.length; i++) {
            LIS[i] = 1;
            for (int j = 0; j < i; j++) {
                if (originalArr[i] > originalArr[j]) {
                    if (LIS[i] < LIS[j] + 1) {
                        LIS[i] = LIS[j] + 1;
                    }
                }
            }
        }

        for (int i = 0; i < LIS.length; i++) {
            System.out.print(LIS[i]);
        }
    }
}
