package ruiliu2.practice.jedisdemo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ruiliu2 on 2017/4/24.
 */
@SpringBootApplication
@ComponentScan(value = {"ruiliu2.practice.jedisdemo.*"})
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
