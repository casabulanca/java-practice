package ruiliu2.practice.elasticsearch.demo.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * spring fox配置管理对象
 * Created by casa on 16/4/29.
 */
@Configuration
@EnableSwagger2
public class SpringfoxConfig {

    /**
     * 屏蔽部分的spring-boot自带的接口
     *
     * @return swagger docket
     */
    @Bean
    public Docket demoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .paths(Predicates.not(PathSelectors.regex("/info.*")))
                .paths(Predicates.not(PathSelectors.regex("/autoconfig.*")))
                .paths(Predicates.not(PathSelectors.regex("/health.*")))
                .paths(Predicates.not(PathSelectors.regex("/metrics.*")))
                .paths(Predicates.not(PathSelectors.regex("/mappings.*")))
                .paths(Predicates.not(PathSelectors.regex("/trace.*")))
                .paths(Predicates.not(PathSelectors.regex("/configprops.*")))
                .paths(Predicates.not(PathSelectors.regex("/beans.*")))
                .paths(Predicates.not(PathSelectors.regex("/env.*")))
                .paths(Predicates.not(PathSelectors.regex("/dump.*")))
                .build();
    }

}
