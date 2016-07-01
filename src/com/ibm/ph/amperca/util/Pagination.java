package com.ibm.ph.amperca.util;

import java.util.List;

import com.ibm.ph.amperca.model.User;


public class Pagination {
    
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalResults;
    
    private String sortFields;
    private String sortDirections;
    private List<User> list;
    
    
    public boolean hasNext() {
        return currentPage < Math.ceil((double) totalResults / (double) pageSize);
    }
    
    public Integer nextPage() {
        return currentPage + 1;
    }
    
    public Integer prevPage() {
        return currentPage - 1;
    }
    
    public boolean hasPrev() {
        return (currentPage > Integer.valueOf(1));
    }

    public String getSortFields() {
        return sortFields;
    }
    public void setSortFields(String sortFields) {
        this.sortFields = sortFields;
    }
    public String getSortDirections() {
        return sortDirections;
    }
    public void setSortDirections(String sortDirections) {
        this.sortDirections = sortDirections;
    }
    public List<User> getList() {
        return list;
    }
    public void setList(List<User> list) {
        this.list = list;
    }
    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public Integer getTotalResults() {
        return totalResults;
    }
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
    
    
    public Integer getStartPage() {
        return (getCurrentPage() - 1) * getPageSize();
    }
    @Override
    public String toString() {
        return "Pagination [Prev="+hasPrev()+",Next="+hasNext()+" currentPage=" + currentPage + ", pageSize=" + pageSize + ", totalResults=" + totalResults + ", sortFields=" + sortFields + ", sortDirections=" + sortDirections + ", list=" + list + "]";
    }
    
}
