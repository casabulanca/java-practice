package ruiliu2.practice.gof.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by casa on 2017/6/9.
 */
public class DynamicProxy<T> implements InvocationHandler {

    private T target;

    @SuppressWarnings(value = {"unchecked"})
    public T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public DynamicProxy(T target) {
        this.target = target;
    }

    private void before() {
        System.out.println("before");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    private void after() {
        System.out.println("after");
    }
}
