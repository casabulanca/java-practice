package ruiliu2.practice.elasticsearch.transportclient;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import ruiliu2.practice.elasticsearch.annotations.HiseePSDocument;
import ruiliu2.practice.elasticsearch.annotations.HiseePSDocumentId;
import ruiliu2.practice.elasticsearch.core.DefaultElasticsearchRepo;
import ruiliu2.practice.elasticsearch.core.ElasticsearchOperations;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * Elasticsearch transport client
 * Created by ruiliu2 on 2017/3/24.
 */
public class Application {

    private static TransportClient client;

    static {
        Settings settings = Settings.builder().put("cluster.name", "IntellijBladeMDF_Cluster").build();
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public static void main(String... args) throws UnknownHostException {
//        System.out.println(JSON.toJSONString(client.listedNodes()));
        ElasticsearchOperations<Document> operations =
                new DefaultElasticsearchRepo<Document>(client, Document.class);
        Document document = new Document();

        document.setRes_id("3217c007-08df-4c9a-892b-8733dae8a6da");
        document.setContent("你好世界杯还有欧洲杯");
//        System.out.println(JSON.toJSONString(operations.create(document)));
//        System.out.println(operations.create(document));
        System.out.println(JSON.toJSONString(operations.matchQueryPageData("content", "世界杯", null)));
    }

    @HiseePSDocument(indexName = "test_index", typeName = "test_type")
    public static class Document {

        @HiseePSDocumentId
        private String res_id;
        private String content;

        /**
         * res_id getter
         *
         * @return res_id
         */
        public String getRes_id() {
            return res_id;
        }

        /**
         * res_id setter
         *
         * @param res_id res_id
         */
        public void setRes_id(String res_id) {
            this.res_id = res_id;
        }

        /**
         * content getter
         *
         * @return content
         */
        public String getContent() {
            return content;
        }

        /**
         * content setter
         *
         * @param content content
         */
        public void setContent(String content) {
            this.content = content;
        }
    }
}
