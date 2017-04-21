package ruiliu2.practice.elasticsearch.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import ruiliu2.practice.elasticsearch.annotations.*;
import ruiliu2.practice.elasticsearch.core.query.Pagination;
import ruiliu2.practice.elasticsearch.core.query.QueryItem;
import ruiliu2.practice.elasticsearch.core.query.SearchBody;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * 默认ESClient的实现
 * Created by ruiliu2 on 2017/4/1.
 */
public class DefaultElasticsearchRepo<T> implements ElasticsearchDocumentOperations<T>, ElasticsearchIndexOperations<T> {

    /**
     * ES Client
     */
    private TransportClient transportClient;

    private ESindex eSindex;

    private Class<T> clazz;

    public DefaultElasticsearchRepo(TransportClient client, Class<T> clazz) {
        this.transportClient = client;
        this.clazz = clazz;
        this.eSindex = esIndex();
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
        XContentDecorator xContentDecorator = new XContentDecorator();
        buildContent(xContentDecorator, instance);
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
    private void buildContent(XContentDecorator xContentDecorator, Object instance) {
        try {
            String docId = "";
            Class<?> clazz = instance.getClass();
            XContentBuilder xContentBuilder;
            if (Objects.isNull(xContentDecorator.getxContentBuilder())) {
                xContentBuilder = jsonBuilder().startObject();
                xContentDecorator.setxContentBuilder(xContentBuilder);
            } else {
                xContentBuilder = xContentDecorator.getxContentBuilder().startObject();
            }
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(instance);
                if (field.isAnnotationPresent(HiseePSDocumentId.class)) {
                    docId = fieldValue.toString();
                }
                //如果是列表
                if (Collection.class.isAssignableFrom(field.getType())) {
                    xContentBuilder.startArray(fieldName);
                    ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                    JSONArray.parseArray(JSON.toJSONString(fieldValue), (Class<?>) parameterizedType.getActualTypeArguments()[0]).forEach((item) -> {
                        buildContent(xContentDecorator, item);
                    });
                    xContentBuilder.endArray();
                }
                //如果是数组
                else if (field.getType().isArray()) {
                    xContentBuilder.startArray(fieldName);

                    JSONArray.parseArray(JSON.toJSONString(fieldValue), field.getType().getComponentType()).forEach((item) -> {
                        buildContent(xContentDecorator, item);
                    });
                    xContentBuilder.endArray();
                } else {
                    xContentBuilder.field(fieldName, fieldValue);
                }
            }
            xContentBuilder.endObject();
            xContentDecorator.setDocumentId(docId);
        } catch (IOException | IllegalAccessException e) {
            //
            e.printStackTrace();
        }
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
        XContentDecorator decorator = new XContentDecorator();
        buildContent(decorator, instance);
        Assert.hasText(decorator.getDocumentId(), "update document without documentId");
        UpdateRequestBuilder builder = transportClient.prepareUpdate(eSindex.getIndexName(), eSindex.getTypeName(), decorator.getDocumentId())
                .setDoc(decorator.getxContentBuilder());
        return builder.get().getId();
    }

    /**
     * match查询单个字段并返回分页数据
     *
     * @param searchBody 查询的内容
     * @return 查询出来的分页数据
     */
    @Override
    public List<T> matchQueryPageData(SearchBody searchBody) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (QueryItem item : searchBody.getQueryItems()) {
            QueryBuilder queryBuilder = QueryBuilders.matchQuery(item.getFieldName(), item.getSearchContent());
            boolQueryBuilder.must().add(queryBuilder);
        }
        SearchRequestBuilder reqBuilder = transportClient.prepareSearch(eSindex.getIndexName()).setTypes(eSindex.getTypeName());
        page(reqBuilder, searchBody.getPagination());


        reqBuilder.setQuery(boolQueryBuilder);

        return resultMapper(reqBuilder.get().getHits().hits());
    }

    /**
     * 处理分页操作
     *
     * @param reqBuilder 请求对象
     * @param pagination 分页对象
     */
    private void page(SearchRequestBuilder reqBuilder, Pagination pagination) {
        if (Objects.nonNull(pagination)) {
            if (pagination.getPageNumber() != 0) {
                reqBuilder.setSize(pagination.getPageSize());
            }

            if (pagination.getPageNumber() != -1) {
                reqBuilder.setFrom(pagination.getPageNumber());
            }

            if (!pagination.getSorts().isEmpty()) {
                for (Pagination.Sort item : pagination.getSorts()) {
                    reqBuilder.addSort(item.getField(), item.getOrder());
                }
            }
        }
    }

    /**
     * 获取所有
     *
     * @param pagination 分页信息
     * @return 转写对象列表
     */
    @Override
    public List<T> all(Pagination pagination) {
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        SearchRequestBuilder requestBuilder = transportClient.prepareSearch(eSindex.getIndexName()).setTypes(eSindex.getTypeName());
        page(requestBuilder, pagination);
        requestBuilder.setQuery(queryBuilder);
        return resultMapper(requestBuilder.get().getHits().hits());
    }

    /**
     * 获取单个转写对象
     *
     * @param id 文档Id
     * @return 文档对象
     */
    @Override
    public T one(String id) {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("_id", id);
        SearchRequestBuilder requestBuilder = transportClient.prepareSearch(eSindex.getIndexName()).setTypes(eSindex.getTypeName());
        requestBuilder.setQuery(queryBuilder);
        return resultMapper(requestBuilder.get().getHits().hits()).get(0);
    }

    /**
     * 查询结果映射
     *
     * @param hits 查询命中列表
     * @return 映射对象列表
     */
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
     * @return ESindex对象
     */
    private ESindex esIndex() {
        ESindex index = new ESindex();
        if (this.clazz.isAnnotationPresent(HiseePSDocument.class)) {
            HiseePSDocument hiseePSDocument = this.clazz.getAnnotation(HiseePSDocument.class);
            index.setIndexName(hiseePSDocument.indexName());
            index.setTypeName(hiseePSDocument.typeName());
            index.setShardsNumber(hiseePSDocument.shardsNumber());
            index.setReplicasNumber(hiseePSDocument.replicasNumber());
        }
        return index;

    }


    /**
     * 创建index
     *
     * @return 创建是否成功
     */
    @Override
    public boolean createIndex() {
        CreateIndexRequestBuilder indexRequestBuilder = transportClient.admin().indices().prepareCreate(esIndex().getIndexName());
        indexRequestBuilder.setSettings(Settings.builder().put("index.number_of_shards", eSindex.getShardsNumber())
                .put("index.number_of_replicas", eSindex.getReplicasNumber()));
        XContentBuilder contentBuilder = null;
        try {
            contentBuilder = jsonBuilder().startObject();
            mappings(contentBuilder, this.clazz);
            contentBuilder.endObject();
            indexRequestBuilder.addMapping(eSindex.getTypeName(), contentBuilder);
            // TODO: 2017/4/13 优化
        } catch (IOException e) {
            e.printStackTrace();
        }
        indexRequestBuilder.get().isAcknowledged();
        return transportClient.admin().indices().prepareRefresh(esIndex().getIndexName()).get().getSuccessfulShards() > 0;
    }

    /**
     * 根据字段类型构建index mappings
     *
     * @return 内容构建器
     */
    private void mappings(XContentBuilder contentBuilder, Class<?> clazz) {
        try {
            contentBuilder = contentBuilder.startObject("properties");
            for (Field field : clazz.getDeclaredFields()) {
                contentBuilder.startObject(field.getName());
                concreteMapping(contentBuilder, field);
                contentBuilder.endObject();
            }
            contentBuilder.endObject();
        } catch (IOException ignored) {
            // TODO: 2017/4/14 LOG
        }
    }

    private void concreteMapping(XContentBuilder xContentBuilder, Field field) {
        //首先判断type
        //如果type为复杂的类型例如object以及nested还需要反射其复杂对象

        HiseePSField hiseePSField = field.getAnnotation(HiseePSField.class);
        try {
            if (hiseePSField.type() == HiseePSFieldType.Object || hiseePSField.type() == HiseePSFieldType.Nested) {

                xContentBuilder.field("type", hiseePSField.type().getValue());
                //如果是列表的话需要遍历实际类型的每个字段
                //如果是map的话需要遍历每个entryset
                //如果是数组需要遍历实际类型的每个字段

                //如果是列表
                if (Collection.class.isAssignableFrom(field.getType())) {
                    ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                    mappings(xContentBuilder, (Class<?>) parameterizedType.getActualTypeArguments()[0]);
                }
                //如果是数组
                else if (field.getType().isArray()) {
                    mappings(xContentBuilder, field.getType().getComponentType());
                }
            } else {
                for (Method annField : hiseePSField.getClass().getDeclaredMethods()) {
                    switch (annField.getName()) {
                        case "type":
                            xContentBuilder.field("type", hiseePSField.type().getValue());
                            break;
                        case "index":
                            xContentBuilder.field("index", hiseePSField.index());
                            break;
                        case "format":
                            if (hiseePSField.format() != DateFormat.none) {
                                xContentBuilder.field("format", hiseePSField.format());
                            }
                            break;
                        case "store":
                            xContentBuilder.field("store", hiseePSField.store());
                            break;
                        case "search_analyzer":
                            String search_analyzer = hiseePSField.search_analyzer();
                            if (!Strings.isNullOrEmpty(search_analyzer)) {
                                xContentBuilder.field("search_analyzer", search_analyzer);
                            }
                            break;
                        case "analyzer":
                            String analyzer = hiseePSField.search_analyzer();
                            if (!Strings.isNullOrEmpty(analyzer)) {
                                xContentBuilder.field("analyzer", analyzer);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * inner class esindex 用于描述数据index信息
     */
    private class ESindex {
        private String indexName;
        private String typeName;
        private int shardsNumber;
        private int replicasNumber;

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

        /**
         * shardsNumber getter
         *
         * @return shardsNumber
         */
        int getShardsNumber() {
            return shardsNumber;
        }

        /**
         * shardsNumber setter
         *
         * @param shardsNumber shardsNumber
         */
        void setShardsNumber(int shardsNumber) {
            this.shardsNumber = shardsNumber;
        }

        /**
         * replicasNumber getter
         *
         * @return replicasNumber
         */
        int getReplicasNumber() {
            return replicasNumber;
        }

        /**
         * replicasNumber setter
         *
         * @param replicasNumber replicasNumber
         */
        void setReplicasNumber(int replicasNumber) {
            this.replicasNumber = replicasNumber;
        }
    }

    /**
     * Elasticsearch xcontent 修饰对象
     */
    private class XContentDecorator {
        private XContentBuilder xContentBuilder;
        private String documentId;

        public XContentDecorator(XContentBuilder xContentBuilder, String documentId) {
            this.xContentBuilder = xContentBuilder;
            this.documentId = documentId;
        }

        XContentDecorator() {
        }

        /**
         * xContentBuilder getter
         *
         * @return xContentBuilder
         */
        XContentBuilder getxContentBuilder() {
            return xContentBuilder;
        }

        /**
         * xContentBuilder setter
         *
         * @param xContentBuilder xContentBuilder
         */
        void setxContentBuilder(XContentBuilder xContentBuilder) {
            this.xContentBuilder = xContentBuilder;
        }

        /**
         * documentId getter
         *
         * @return documentId
         */
        String getDocumentId() {
            return documentId;
        }

        /**
         * documentId setter
         *
         * @param documentId documentId
         */
        void setDocumentId(String documentId) {
            this.documentId = documentId;
        }
    }


}
