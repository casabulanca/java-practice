package ruiliu2.practice.gof.proxy.staticproxy;

/**
 * Created by casa on 2017/6/9.
 */
public class HelloProxy implements Hello {

    private Hello entity;

    /**
     * 这里的实现的初始化的时机
     *
     * @param entity
     */
    public HelloProxy(Hello entity) {
        this.entity = entity;
    }

    public HelloProxy() {

    }

    private void before() {
        System.out.println("before");
    }

    public void say(String message) {
        this.before();
        this.entity.say(message);
        this.after();
    }

    private void after() {
        System.out.println("after");
    }
}
