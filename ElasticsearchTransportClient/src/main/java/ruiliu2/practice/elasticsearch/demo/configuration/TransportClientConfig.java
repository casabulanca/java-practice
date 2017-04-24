package ruiliu2.practice.elasticsearch.demo.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import ruiliu2.practice.elasticsearch.core.DefaultElasticsearchRepo;
import ruiliu2.practice.elasticsearch.demo.entities.TransEntity;
import ruiliu2.practice.elasticsearch.demo.entities.TransText;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 运行时分析
 * Created by ruiliu2@iflytek,com on 2017/4/20.
 */
@Configuration
public class TransportClientConfig {

    @Bean
    public TransportClient transportClient() {
        Settings settings = Settings.builder().put("cluster.name", "IntellijBladeMDF_Cluster").build();
        InetSocketTransportAddress inetSocketTransportAddress = null;
        try {
            inetSocketTransportAddress = new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return new PreBuiltTransportClient(settings)
                .addTransportAddress(inetSocketTransportAddress);
    }

    @Bean
    public DefaultElasticsearchRepo<TransEntity> transEntityDefaultElasticsearchRepo(TransportClient transportClient) {
        return new DefaultElasticsearchRepo<>(transportClient, TransEntity.class);
    }

    @Bean
    public DefaultElasticsearchRepo<TransText> transTextDefaultElasticsearchRepo(TransportClient transportClient) {
        return new DefaultElasticsearchRepo<>(transportClient, TransText.class);
    }

    /**
     * 处理ajax数据请求问题
     *
     * @return json转换器
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }
}
