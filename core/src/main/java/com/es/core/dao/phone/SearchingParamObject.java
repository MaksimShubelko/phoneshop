package com.es.core.dao.phone;

public class SearchingParamObject {

    public static final int DEFAULT_PAGE = 1;
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_ORDER = "ASC";

    public static final int DEFAULT_PHONES_PER_PAGE = 7;

    public static final int DEFAULT_OFFSET = 0;

    public static final String DEFAULT_SEARCHING_TERM = "";

    private String sortBy = DEFAULT_SORT_BY;

    private String sortOrder = DEFAULT_SORT_ORDER;

    private int phonesPerPage = DEFAULT_PHONES_PER_PAGE;

    private int offset = DEFAULT_OFFSET;

    private String term = DEFAULT_SEARCHING_TERM;

    private int page = DEFAULT_PAGE;

    private SearchingParamObject(Builder builder) {
        sortBy = builder.sortBy != null && !builder.sortBy.isEmpty() ? builder.sortBy : sortBy;
        sortOrder = builder.sortOrder != null && !builder.sortOrder.isEmpty()
                && (builder.sortOrder.equalsIgnoreCase("asc")
                || builder.sortOrder.equalsIgnoreCase("desc")) ? builder.sortOrder : sortOrder;
        phonesPerPage = builder.phonesPerPage > 0 ? builder.phonesPerPage : phonesPerPage;
        offset = builder.offset > 0 ? builder.offset : offset;
        term = builder.term != null && !builder.term.isEmpty() ? builder.term : term;
        page = builder.page > 0 ? builder.page : page;

    }

    public String getSortBy() {
        return sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public int getPhonesPerPage() {
        return phonesPerPage;
    }

    public int getOffset() {
        return offset;
    }

    public String getTerm() {
        return term;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setPhonesPerPage(int phonesPerPage) {
        this.phonesPerPage = phonesPerPage;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String sortBy;

        private String sortOrder;

        private int phonesPerPage;

        private int offset;

        private String term;

        private int page;

        private Builder() {
        }

        public Builder sortBy(String sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public Builder sortOrder(String sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public Builder phonesPerPage(int phonesPerPage) {
            this.phonesPerPage = phonesPerPage;
            return this;
        }

        public Builder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder term(String term) {
            this.term = term;
            return this;
        }

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public SearchingParamObject build() {
            return new SearchingParamObject(this);
        }

    }

}
