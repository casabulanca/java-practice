package domain;

import ruiliu2.practice.elasticsearch.annotations.HiseePSDocument;
import ruiliu2.practice.elasticsearch.annotations.HiseePSDocumentId;

/**
 * 测试文档实体对象
 * Created by ruiliu2 on 2017/4/10.
 */
@HiseePSDocument(indexName = "test_index", typeName = "test_type")
public class TestDocument {

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
