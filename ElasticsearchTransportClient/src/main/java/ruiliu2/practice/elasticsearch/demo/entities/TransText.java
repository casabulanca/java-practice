package ruiliu2.practice.elasticsearch.demo.entities;


import ruiliu2.practice.elasticsearch.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 翻译文本
 * Created by ruiliu2 on 2017/4/24.
 */
@HiseePSDocument(indexName = "hiseeps-ed", typeName = "hiseeps-ed", shardsNumber = 3, replicasNumber = 1)
@HiseeObjectClass
public class TransText {

    @HiseePSDocumentId
    @HiseePSField(type = HiseePSFieldType.Keyword, index = HiseePSFieldIndex.not_analyzed)
    private String id;

    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.analyzed, analyzer = "ik_max_word", search_analyzer = "ik_max_word")
    private String fullText;

    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.no)
    private String html;

    @HiseePSField(type = HiseePSFieldType.Object)
    private Map<String, List<Lattice>> lattices;


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

    /**
     * lattices getter
     *
     * @return lattices
     */
    public Map<String, List<Lattice>> getLattices() {
        return lattices;
    }

    /**
     * lattices setter
     *
     * @param lattices lattices
     */
    public void setLattices(Map<String, List<Lattice>> lattices) {
        this.lattices = lattices;
    }
}
