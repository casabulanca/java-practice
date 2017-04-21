package ruiliu2.practice.elasticsearch.demo.entities;

import ruiliu2.practice.elasticsearch.annotations.*;

import java.util.List;

/**
 * 转写实体
 * Created by ruiliu2 on 2017/4/20.
 */
@HiseePSDocument(indexName = "hiseeps_trans", typeName = "hiseeps_trans", shardsNumber = 3, replicasNumber = 1)
public class TransEntity {

    @HiseePSDocumentId
    @HiseePSField(type = HiseePSFieldType.Keyword, index = HiseePSFieldIndex.not_analyzed)
    private String id;

    @HiseePSField(type = HiseePSFieldType.Object)
    private List<Lattice> lattices;

    @HiseePSField(type = HiseePSFieldType.Text)
    private String fullText;

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
}
