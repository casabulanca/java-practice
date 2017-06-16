package ruiliu2.practice.JDKAnalysis.basic.string;

/**
 * Created by casa on 2017/6/11.
 */
public class StringApplication {

    private static final int loop = 1000000;

    public static void main(String... args) {
        StringBuilder sbuilder = new StringBuilder();
        StringBuffer sbuffer = new StringBuffer();
        String string = new String();

        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            sbuffer.append("hello");
        }
        string = sbuffer.toString();
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            sbuilder.append("hello");
        }

        string = sbuilder.toString();
        System.out.println(System.currentTimeMillis() - start);

    }
}
