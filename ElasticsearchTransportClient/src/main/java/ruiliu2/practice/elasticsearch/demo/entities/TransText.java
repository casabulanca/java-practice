package ruiliu2.practice.elasticsearch.demo.entities;

import ruiliu2.practice.elasticsearch.annotations.*;

/**
 * 翻译文本
 * Created by ruiliu2 on 2017/4/24.
 */
@HiseePSDocument(indexName = "hiseeps-edit", typeName = "hiseeps-edit", shardsNumber = 3, replicasNumber = 1)
public class TransText {

    @HiseePSDocumentId
    @HiseePSField(type = HiseePSFieldType.Keyword, index = HiseePSFieldIndex.not_analyzed)
    private String id;

    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.analyzed, analyzer = "ik_max_word", search_analyzer = "ik_max_word")
    private String fullText;

    @HiseePSField(type = HiseePSFieldType.Keyword, index = HiseePSFieldIndex.not_analyzed)
    private String html;

    /**
     * id getter
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * id setter
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * fullText getter
     *
     * @return fullText
     */
    public String getFullText() {
        return fullText;
    }

    /**
     * fullText setter
     *
     * @param fullText fullText
     */
    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    /**
     * html getter
     *
     * @return html
     */
    public String getHtml() {
        return html;
    }

    /**
     * html setter
     *
     * @param html html
     */
    public void setHtml(String html) {
        this.html = html;
    }
}
