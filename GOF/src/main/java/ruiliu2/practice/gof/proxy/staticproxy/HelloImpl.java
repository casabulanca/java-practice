package ruiliu2.practice.gof.proxy.staticproxy;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

/**
 * Created by casa on 2017/6/9.
 */
public class HelloImpl implements Hello {

    public void say(String message) {
        System.out.println(message);
    }
}
