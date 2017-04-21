package ruiliu2.practice.elasticsearch.annotations;

/**
 * hiseePS ES字段类型
 * Created by ruiliu2 on 2017/4/13.
 */
public enum HiseePSFieldType {

    Text("text"),
    Keyword("keyword"),
    Integer("integer"),
    Long("long"),
    Date("date"),
    Float("float"),
    Double("double"),
    Boolean("boolean"),
    Object("object"),
    Auto("auto"),
    Nested("nested"),
    Ip("ip"),
    Binary("binary");

    private String value;

    private HiseePSFieldType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
