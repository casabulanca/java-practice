package ruiliu2.practice.elasticsearch.core.query;

import java.util.List;

/**
 * 查询对象
 * Created by ruiliu2 on 2017/4/21.
 */
public class SearchBody {
    
    private List<QueryItem> queryItems;
    private Pagination pagination;

    /**
     * queryItems getter
     *
     * @return queryItems
     */
    public List<QueryItem> getQueryItems() {
        return queryItems;
    }

    /**
     * queryItems setter
     *
     * @param queryItems queryItems
     */
    public void setQueryItems(List<QueryItem> queryItems) {
        this.queryItems = queryItems;
    }

    /**
     * pagination getter
     *
     * @return pagination
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * pagination setter
     *
     * @param pagination pagination
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
