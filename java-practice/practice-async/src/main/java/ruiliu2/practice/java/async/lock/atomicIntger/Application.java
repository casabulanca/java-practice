package ruiliu2.practice.java.async.lock.atomicIntger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * atomic Intger
 * Created by ruiliu2@.com on 2016/8/10.
 */
@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = "ruiliu2.practice.*")
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
