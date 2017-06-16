package ruiliu2.practice.elasticsearch.core.query;

import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页对象
 * Created by ruiliu2 on 2017/4/12.
 */
public class Pagination {

    /**
     * properties
     */
    private int pageSize;
    private int pageNumber;
    private List<Sort> sorts = new ArrayList<Sort>();

    /**
     * pageSieze getter
     *
     * @return pageSieze
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * pageSieze setter
     *
     * @param pageSieze pageSieze
     */
    public void setPageSize(int pageSieze) {
        this.pageSize = pageSieze;
    }

    /**
     * pageNumber getter
     *
     * @return pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * pageNumber setter
     *
     * @param pageNumber pageNumber
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * sorts getter
     *
     * @return sorts
     */
    public List<Sort> getSorts() {
        return sorts;
    }

    public Pagination() {
    }

    private Pagination(PaginationBuilder builder) {
        this.pageSize = builder.pageSize;
        this.pageNumber = builder.pageNumber;
        for (Map.Entry<String, SortOrder> item : builder.sortList.entrySet()) {
            this.sorts.add(new Sort(item.getKey(), item.getValue()));
        }
    }

    public static class PaginationBuilder {

        /**
         * build properties
         */
        private int pageSize = 0;
        private int pageNumber = -1;
        private Map<String, SortOrder> sortList = new HashMap<>();

        public PaginationBuilder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public PaginationBuilder pageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public PaginationBuilder sort(String field, SortOrder order) {
            this.sortList.put(field, order);
            return this;
        }

        public Pagination build() {
            return new Pagination(this);
        }
    }


    public class Sort {
        private String field;
        private SortOrder order;

        public Sort(String field, SortOrder order) {
            this.field = field;
            this.order = order;
        }

        /**
         * field getter
         *
         * @return field
         */
        public String getField() {
            return field;
        }

        /**
         * field setter
         *
         * @param field field
         */
        public void setField(String field) {
            this.field = field;
        }

        /**
         * order getter
         *
         * @return order
         */
        public SortOrder getOrder() {
            return order;
        }

        /**
         * order setter
         *
         * @param order order
         */
        public void setOrder(SortOrder order) {
            this.order = order;
        }
    }
}
