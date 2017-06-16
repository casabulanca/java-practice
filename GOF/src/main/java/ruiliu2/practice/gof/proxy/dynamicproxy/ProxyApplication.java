package ruiliu2.practice.gof.proxy.dynamicproxy;

import ruiliu2.practice.gof.proxy.staticproxy.Hello;
import ruiliu2.practice.gof.proxy.staticproxy.HelloImpl;
import ruiliu2.practice.gof.proxy.staticproxy.HelloProxy;
import sun.misc.VM;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.lang.reflect.Proxy;

/**
 * Created by casa on 2017/6/9.
 */
public class ProxyApplication {

    @CallerSensitive
    public static void main(String... args) {

        long start = System.currentTimeMillis();
        Hello hello = new HelloImpl();
        DynamicProxy<Hello> proxy = new DynamicProxy<>(hello);

        Hello zzzz = proxy.getProxy();

        zzzz.say("今天天气不错");

        System.out.println(System.currentTimeMillis() - start);
    }
}
