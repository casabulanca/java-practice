package ruiliu2.practice.elasticsearch.demo.entities;

import ruiliu2.practice.elasticsearch.annotations.HiseePSDocument;
import ruiliu2.practice.elasticsearch.annotations.HiseePSField;
import ruiliu2.practice.elasticsearch.annotations.HiseePSFieldType;

import java.util.Map;

/**
 * Created by casa on 2017/5/12.
 */
@HiseePSDocument(shardsNumber = 1, replicasNumber = 1, indexName = "test", typeName = "test")
public class TestObject {

    @HiseePSField(type = HiseePSFieldType.Object)
    private Map<String, Lattice> datas;


    /**
     * datas getter
     *
     * @return datas
     */
    public Map<String, Lattice> getDatas() {
        return datas;
    }

    /**
     * datas setter
     *
     * @param datas datas
     */
    public void setDatas(Map<String, Lattice> datas) {
        this.datas = datas;
    }
}
