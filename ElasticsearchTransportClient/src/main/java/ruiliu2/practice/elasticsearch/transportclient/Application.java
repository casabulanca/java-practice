package ruiliu2.practice.elasticsearch.transportclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.net.UnknownHostException;

/**
 * Elasticsearch transport client
 * Created by ruiliu2 on 2017/3/24.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"ruiliu2.practice.elasticsearch.*"})
public class Application {

    public static void main(String... args) throws UnknownHostException {
//        System.out.println(JSON.toJSONString(client.listedNodes()));
        SpringApplication.run(Application.class, args);
    }
}
