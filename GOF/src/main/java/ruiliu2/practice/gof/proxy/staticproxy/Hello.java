package ruiliu2.practice.gof.proxy.staticproxy;

import sun.reflect.CallerSensitive;

/**
 * Created by casa on 2017/6/9.
 */
public interface Hello {

    @CallerSensitive
    void say(String message);
}
