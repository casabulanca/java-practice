package ruiliu2.practice.elasticsearch.demo.entities;


import ruiliu2.practice.elasticsearch.annotations.*;

import java.util.List;

/**
 * 转写实体
 * Created by ruiliu2 on 2017/4/20.
 */
@HiseePSDocument(indexName = "hiseeps_or", typeName = "hiseeps_or", shardsNumber = 3, replicasNumber = 1)
public class TransEntity {

    @HiseePSDocumentId
    @HiseePSField(type = HiseePSFieldType.Keyword, index = HiseePSFieldIndex.not_analyzed)
    private String id;

    @HiseePSField(type = HiseePSFieldType.Object)
    private List<Lattice> lattices;

    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.no)
    private List<String> translateContent;

    @HiseePSField(type = HiseePSFieldType.Text, index = HiseePSFieldIndex.analyzed, analyzer = "ik_max_word", search_analyzer = "ik_max_word")
    private String summary;

    @HiseePSField(type = HiseePSFieldType.Object)
    private TransText transText;

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
     * lattices getter
     *
     * @return lattices
     */
    public List<Lattice> getLattices() {
        return lattices;
    }

    /**
     * lattices setter
     *
     * @param lattices lattices
     */
    public void setLattices(List<Lattice> lattices) {
        this.lattices = lattices;
    }

    /**
     * translateContent getter
     *
     * @return translateContent
     */
    public List<String> getTranslateContent() {
        return translateContent;
    }

    /**
     * translateContent setter
     *
     * @param translateContent translateContent
     */
    public void setTranslateContent(List<String> translateContent) {
        this.translateContent = translateContent;
    }

    /**
     * summary getter
     *
     * @return summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * summary setter
     *
     * @param summary summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * transText getter
     *
     * @return transText
     */
    public TransText getTransText() {
        return transText;
    }

    /**
     * transText setter
     *
     * @param transText transText
     */
    public void setTransText(TransText transText) {
        this.transText = transText;
    }
}
