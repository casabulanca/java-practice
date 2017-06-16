package domain;


import ruiliu2.practice.elasticsearch.annotations.*;

import java.util.List;

/**
 * 测试文档实体对象
 * Created by ruiliu2 on 2017/4/10.
 */
@HiseePSDocument(indexName = "test_index_code", typeName = "test_type_code")
public class TestDocument {

    @HiseePSDocumentId
    @HiseePSField(type = HiseePSFieldType.Keyword)
    private String res_id;
    @HiseePSField(type = HiseePSFieldType.Object)
    private List<JSONContent> contents;
    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.analyzed, search_analyzer = "ik_max_word", analyzer = "ik_max_word")
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
     * contents getter
     *
     * @return contents
     */
    public List<JSONContent> getContents() {
        return contents;
    }

    /**
     * contents setter
     *
     * @param contents contents
     */
    public void setContents(List<JSONContent> contents) {
        this.contents = contents;
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
