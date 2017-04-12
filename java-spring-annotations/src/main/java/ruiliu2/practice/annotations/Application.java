package ruiliu2.practice.annotations;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by casa on 2017/3/31.
 */
@Component
public class Application implements ApplicationContextAware {

    private static ApplicationContext appContext;

    public static void main(String... args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ruiliu2.practice.annotations");
        ApplicationContext appContext = new AnnotationConfigApplicationContext("ruiliu2.practice.annotations");
//        System.out.println(context.getBeanDefinitionCount());
        System.out.println(appContext.getBeanDefinitionCount());
//        DemoService ds = context.getBean(DemoService.class);
//        ds.doSomething();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }
}
