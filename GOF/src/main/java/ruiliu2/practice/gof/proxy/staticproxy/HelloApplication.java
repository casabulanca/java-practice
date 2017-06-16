package ruiliu2.practice.gof.proxy.staticproxy;

/**
 * Created by casa on 2017/6/9.
 */
public class HelloApplication {

    public static void main(String... args) {
        HelloProxy proxy = new HelloProxy(new HelloImpl());
        proxy.say("今天天气不错");
    }
}
