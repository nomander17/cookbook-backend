package com.major.cookbook.dto;

public class SearchDTO {
    
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String search) {
        this.query = search;
    }

    public SearchDTO(String search) {
        this.query = search;
    }
    
    public SearchDTO(){

    }

    @Override
    public String toString() {
        return query;
    }    
}
