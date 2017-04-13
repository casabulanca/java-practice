package ruiliu2.practice.elasticsearch.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import ruiliu2.practice.elasticsearch.annotations.HiseePSDocument;
import ruiliu2.practice.elasticsearch.annotations.HiseePSDocumentId;
import ruiliu2.practice.elasticsearch.core.query.Pagination;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 默认ESClient的实现
 * Created by ruiliu2 on 2017/4/1.
 */
public class DefaultElasticsearchRepo<T> implements ElasticsearchDocumentOperations<T> {

    /**
     * ES Client
     */
    private TransportClient transportClient;

    private ESindex eSindex;

    private Class<T> clazz;

    public DefaultElasticsearchRepo(TransportClient client, Class<T> clazz) {
        this.transportClient = client;
        this.clazz = clazz;
        this.eSindex = esIndex(clazz);
    }


    /**
     * 插入Document
     *
     * @param instance 插入的Document实体
     * @return 插入的实体
     */
    @Override
    public T create(T instance) {
        String documentId;
        XContentDecorator xContentDecorator = buildContent(instance);
        String docId = xContentDecorator.getDocumentId();
        IndexRequestBuilder builder = transportClient.prepareIndex(eSindex.getIndexName(), eSindex.getTypeName()).
                setSource(xContentDecorator.getxContentBuilder());
        documentId = StringUtils.isEmpty(docId) ? builder.get().getId() : builder.setId(docId).get().getId();
        return StringUtils.isEmpty(documentId) || !docId.equals(documentId) ? null : instance;
    }

    /**
     * 针对新增和更新构建XContent
     *
     * @param instance 实体对象
     * @return XContent
     */
    private XContentDecorator buildContent(T instance) {
        XContentDecorator xContentDecorator = new XContentDecorator();
        try {
            String docId = "";
            Class<?> clazz = instance.getClass();
            XContentBuilder xContentBuilder = jsonBuilder().startObject();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(instance);
                if (field.isAnnotationPresent(HiseePSDocumentId.class)) {
                    docId = fieldValue.toString();
                }
                xContentBuilder.field(fieldName, fieldValue);
            }
            xContentBuilder.endObject();
            xContentDecorator.setDocumentId(docId);
            xContentDecorator.setxContentBuilder(xContentBuilder);
        } catch (IOException e) {
            //
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return xContentDecorator;
    }

    /**
     * 删除Document
     *
     * @param id Document ID
     * @return 删除的Document ID
     */
    @Override
    public String delete(String id) {
        return transportClient.prepareDelete(eSindex.getIndexName(), eSindex.getTypeName(), id).get().getId();
    }


    /**
     * 更新操作
     *
     * @param instance 更新的对象实例
     * @return 更新的文档Id
     */
    @Override
    public String update(T instance) {
        XContentDecorator decorator = buildContent(instance);
        Assert.hasText(decorator.getDocumentId(), "update document without documentId");
        UpdateRequestBuilder builder = transportClient.prepareUpdate(eSindex.getIndexName(), eSindex.getTypeName(), decorator.getDocumentId())
                .setDoc(decorator.getxContentBuilder());
        return builder.get().getId();
    }

    @Override
    public List<T> matchQueryPageData(String fieldName, String searchBody, Pagination pagination) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery(fieldName, searchBody);
        SearchRequestBuilder resp = transportClient.prepareSearch(eSindex.getIndexName()).setTypes(eSindex.getTypeName());
        if (Objects.nonNull(pagination)) {
            if (pagination.getPageNumber() != 0) {
                resp.setSize(pagination.getPageSize());
            }

            if (pagination.getPageNumber() != -1) {
                resp.setFrom(pagination.getPageNumber());
            }

            if (!pagination.getSorts().isEmpty()) {
                for (Pagination.Sort item : pagination.getSorts()) {
                    resp.addSort(item.getField(), item.getOrder());
                }
            }
        }

        resp.setQuery(queryBuilder);

        return resultMapper(resp.get().getHits().hits());
    }

    private List<T> resultMapper(SearchHit[] hits) {
        List<T> result = new ArrayList<T>();
        for (SearchHit item : hits) {
            result.add(JSONObject.parseObject(JSON.toJSONString(item.getSource()), clazz));
        }
        return result;
    }

    /**
     * 根据注解获取elasticsearch node index信息
     *
     * @param clazz 实体对象类型
     * @return ESindex对象
     */
    private ESindex esIndex(Class<?> clazz) {
        ESindex index = new ESindex();
        if (clazz.isAnnotationPresent(HiseePSDocument.class)) {
            HiseePSDocument hiseePSDocument = clazz.getAnnotation(HiseePSDocument.class);
            index.setIndexName(hiseePSDocument.indexName());
            index.setTypeName(hiseePSDocument.typeName());
        }
        return index;

    }

    /**
     * inner class esindex 用于描述数据index信息
     */
    private class ESindex {
        private String indexName;
        private String typeName;

        /**
         * indexName getter
         *
         * @return indexName
         */
        String getIndexName() {
            return indexName;
        }

        /**
         * indexName setter
         *
         * @param indexName indexName
         */
        void setIndexName(String indexName) {
            this.indexName = indexName;
        }

        /**
         * typeName getter
         *
         * @return typeName
         */
        String getTypeName() {
            return typeName;
        }

        /**
         * typeName setter
         *
         * @param typeName typeName
         */
        void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }

    /**
     *
     */
    private class XContentDecorator {
        private XContentBuilder xContentBuilder;
        private String documentId;

        public XContentDecorator(XContentBuilder xContentBuilder, String documentId) {
            this.xContentBuilder = xContentBuilder;
            this.documentId = documentId;
        }

        public XContentDecorator() {
        }

        /**
         * xContentBuilder getter
         *
         * @return xContentBuilder
         */
        public XContentBuilder getxContentBuilder() {
            return xContentBuilder;
        }

        /**
         * xContentBuilder setter
         *
         * @param xContentBuilder xContentBuilder
         */
        public void setxContentBuilder(XContentBuilder xContentBuilder) {
            this.xContentBuilder = xContentBuilder;
        }

        /**
         * documentId getter
         *
         * @return documentId
         */
        public String getDocumentId() {
            return documentId;
        }

        /**
         * documentId setter
         *
         * @param documentId documentId
         */
        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }
    }


}
