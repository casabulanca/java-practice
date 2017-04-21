package ruiliu2.practice.elasticsearch.core.query;

/**
 * 查询项
 * Created by ruiliu2 on 2017/4/21.
 */
public class QueryItem {

    private String fieldName;
    private String searchContent;


    /**
     * fieldName getter
     *
     * @return fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * fieldName setter
     *
     * @param fieldName fieldName
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * searchContent getter
     *
     * @return searchContent
     */
    public String getSearchContent() {
        return searchContent;
    }

    /**
     * searchContent setter
     *
     * @param searchContent searchContent
     */
    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
}
