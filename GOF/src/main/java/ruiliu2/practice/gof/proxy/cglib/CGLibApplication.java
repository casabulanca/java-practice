package ruiliu2.practice.gof.proxy.cglib;

import ruiliu2.practice.gof.proxy.staticproxy.Hello;
import ruiliu2.practice.gof.proxy.staticproxy.HelloImpl;
import ruiliu2.practice.gof.proxy.staticproxy.HelloProxy;

/**
 * Created by casa on 2017/6/10.
 */
public class CGLibApplication {

    public static void main(String... args) {
        long start = System.currentTimeMillis();
        Hello helloCGLogProxy = new CGLogProxy<>(new HelloImpl()).getProxy(HelloImpl.class);

        helloCGLogProxy.say("今天天气不错");
        System.out.println(System.currentTimeMillis() - start);

    }
}
